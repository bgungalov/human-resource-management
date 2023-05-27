package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    /**
     * Find all notifications where the receiverId is equal to the given receiverId.
     *
     * @param receiverId The id of the user who is receiving the notification.
     * @return A list of notifications that have the receiverId.
     */
    List<Notification> findByReceiverId(int receiverId);

    /**
     * Find all notifications where the senderId is equal to the given senderId and the receiverId is equal to the given
     * receiverId.
     *
     * @param senderId   The id of the user who sent the notification.
     * @param receiverId The user id of the user who is receiving the notification.
     * @return A list of notifications that have the same senderId and receiverId.
     */
    List<Notification> findBySenderIdAndReceiverId(int senderId, int receiverId);

    /**
     * Save a new notification to the database.
     *
     * @param notification The notification object to be saved.
     * @return The notification that was saved.
     */
    Notification saveNewNotification(Notification notification);

    /**
     * Update the notification with the given id.
     *
     * @param notification The notification object that you want to update.
     * @param id           The id of the notification to update.
     * @return The updated notification.
     */
    Notification updateNotification(Notification notification, int id);

    /**
     * Deletes the record with the given id.
     *
     * @param id The id of the Notification to be deleted.
     */
    void deleteById(int id);
}
