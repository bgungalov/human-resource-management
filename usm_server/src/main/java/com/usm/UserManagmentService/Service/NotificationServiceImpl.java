package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Notification;
import com.usm.UserManagmentService.Repository.NotificationRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.usm.UserManagmentService.Constants.TYPES_OF_NOTIFICATION.DIRECT_MESSAGE;
import static com.usm.UserManagmentService.Constants.TYPES_OF_NOTIFICATION.SYSTEM;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Finds all notifications by receiver id
     *
     * @param receiverId The id of the user who is receiving the notification.
     * @return A list of notifications
     */
    @Override
    public List<Notification> findByReceiverId(int receiverId) {

        return notificationRepository.findByReceiverId(receiverId);
    }

    /**
     * Finds all notifications by senderId and receiverId
     *
     * @param senderId   The id of the user who sent the notification.
     * @param receiverId The user who is receiving the notification.
     * @return A list of notifications
     */
    @Override
    public List<Notification> findBySenderIdAndReceiverId(int senderId, int receiverId) {

        return notificationRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    /**
     * Save new Notification to the database
     *
     * @param notification The notification object that is being saved.
     * @return A notification object.
     */
    @Override
    public Notification saveNewNotification(Notification notification) {

        switch (notification.getType()) {
            case SYSTEM -> notification.setType(SYSTEM);
            case DIRECT_MESSAGE -> notification.setType(DIRECT_MESSAGE);
        }

        return notificationRepository.save(notification);
    }

    /**
     * It updates a notification in the database.
     *
     * @param notification The notification object that is being updated.
     * @param id           The id of the notification to be updated.
     * @return The updated notification is being returned.
     */
    @Override
    public Notification updateNotification(Notification notification, int id) {

        Optional<Notification> notificationDb = Optional.of(notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id)));

        notificationDb.get().setSenderId(notification.getSenderId());
        notificationDb.get().setSender(notification.getSender());
        notificationDb.get().setReceiverId(notification.getReceiverId());
        notificationDb.get().setBody(notification.getBody());
        notificationDb.get().setType(notification.getType());
        notificationDb.get().setCreatedDate(notification.getCreatedDate());
        notificationDb.get().setNew(notification.isNew());

        return notificationRepository.save(notificationDb.get());
    }

    /**
     * It deletes a notification by its id.
     *
     * @param id The id of the notification to be deleted.
     */
    @Override
    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }
}
