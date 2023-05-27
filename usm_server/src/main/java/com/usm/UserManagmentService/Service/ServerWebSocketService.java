package com.usm.UserManagmentService.Service;

import com.google.gson.Gson;
import com.usm.UserManagmentService.Constants.TYPES_OF_NOTIFICATION;
import com.usm.UserManagmentService.Entity.Message;
import com.usm.UserManagmentService.Entity.Notification;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Repository.MessageRepository;
import com.usm.UserManagmentService.Utils.CustomMessageRecourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalTime;
import java.util.*;

/**
 * > This class is a WebSocket handler that implements the `SubProtocolCapable` interface
 */
public class ServerWebSocketService extends TextWebSocketHandler implements SubProtocolCapable {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ServerWebSocketService.class);

    private final Map<String, List<WebSocketSession>> sessions = new HashMap<>();

    /**
     * The function is called when a new connection is established. It adds the new session to the list of sessions
     *
     * @param session The WebSocketSession object that represents the connection between the client and the server.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection opened" + Objects.requireNonNull(session.getUri()).toString());

        if (sessions.containsKey(session.getUri().toString())) {
            sessions.get(session.getUri().toString()).add(session);
        } else {
            List<WebSocketSession> currentSession = new LinkedList<>();
            currentSession.add(session);
            sessions.put(session.getUri().toString(), currentSession);
        }
        TextMessage message = new TextMessage("one-time message from server" + session.getUri());
        logger.info("Server sends: {}", message);
        session.sendMessage(message);
    }

    /**
     * When the connection is closed, remove the session from the list of sessions.
     *
     * @param session The WebSocketSession object that represents the connection.
     * @param status  The status code of the close event.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Server connection closed: {}", status);

        sessions.get(Objects.requireNonNull(session.getUri()).toString()).removeIf(each -> !each.isOpen());
    }

    /**
     * It takes a message from the client, saves it to the database, creates a notification if necessary, and sends the
     * message to all clients connected to the same chat
     *
     * @param session The WebSocket session that the message was received on.
     * @param message The message received from the client.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        Gson gson = new Gson();

        Message newMessage = gson.fromJson(message.getPayload(), Message.class);
        Message messageDb = new Message(newMessage.getSenderId(),
                newMessage.getReceiverId(),
                newMessage.getBody(),
                newMessage.isNew(),
                newMessage.getUpdateDate(),
                newMessage.getCommunication());

        messageRepository.save(messageDb);

        if (sessions.get(Objects.requireNonNull(session.getUri()).toString()).size() == 1) {
            createNotificationIfNecessary(messageDb);
        }

        for (WebSocketSession each : sessions.get(session.getUri().toString())) {
            logger.info("Server sends: {}", each);
            logger.info("isOPEN: {}", each.isOpen());

            if (each.isOpen()) {
                String broadcast = "server periodic message " + LocalTime.now();
                logger.info("Server sends: {}", broadcast);
                each.sendMessage(message);
            }
        }
    }

    /**
     * This function is called when the server encounters an error while sending a message to the client.
     *
     * @param session   The WebSocket session that the client is connected to.
     * @param exception The exception that occurred.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Server transport error: {}", exception.getMessage());
    }

    /**
     * > This function returns a list of subprotocols that the client can use to communicate with the server
     *
     * @return A list of subprotocols that the server supports.
     */
    @Override
    public List<String> getSubProtocols() {

        return Collections.singletonList("subprotocol.demo.websocket");
    }

    /**
     * If there is no notification for the sender and receiver, create a new notification. If there is a notification,
     * update the date and set the notification to new
     *
     * @param message the message that was sent
     */
    private void createNotificationIfNecessary(Message message) {

        List<Notification> notificationsList = notificationService.findBySenderIdAndReceiverId(message.getSenderId(), message.getReceiverId());

        if (notificationsList == null || notificationsList.isEmpty()) {
            User user = userService.getUserById(message.getSenderId());
            String nickname = user.getFirstName() + " " + user.getLastName();
            notificationService.saveNewNotification(new Notification(1, message.getSenderId(),
                    nickname, message.getReceiverId(), CustomMessageRecourse.directMessageNotification + nickname,
                    TYPES_OF_NOTIFICATION.DIRECT_MESSAGE, new Date(), true));
        } else {
            Notification foundNotification = notificationsList.stream().filter(item -> item.getSenderId() == message.getSenderId()).findAny().orElse(null);
            Objects.requireNonNull(foundNotification).setCreatedDate(new Date());
            foundNotification.setNew(true);
            notificationService.updateNotification(foundNotification, foundNotification.getId());
        }
    }
}
