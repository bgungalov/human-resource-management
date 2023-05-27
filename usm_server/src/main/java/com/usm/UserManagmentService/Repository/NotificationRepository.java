package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository for the Notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    /**
     * This function returns a list of notifications that have the receiverId that is passed in as a parameter
     *
     * @param receiverId The id of the user who is receiving the notification.
     * @return A list of notifications that have the receiverId.
     */
    @Query(value = "SELECT * FROM users.notification WHERE users.notification.receiver_id = :receiverId", nativeQuery = true)
    List<Notification> findByReceiverId(@Param("receiverId") int receiverId);

    /**
     * Find all notifications where the senderId is equal to the given senderId and the receiverId is equal to the given
     * receiverId.
     *
     * @param senderId   The id of the user who sent the notification.
     * @param receiverId The user id of the user who is receiving the notification.
     * @return A list of notifications that have the same senderId and receiverId.
     */
    List<Notification> findBySenderIdAndReceiverId(int senderId, int receiverId);
}
