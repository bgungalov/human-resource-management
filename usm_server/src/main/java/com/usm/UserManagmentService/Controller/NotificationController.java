package com.usm.UserManagmentService.Controller;

import com.usm.UserManagmentService.Entity.Notification;
import com.usm.UserManagmentService.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * GET Method for getting all notifications by receiverId.
     *
     * @param receiverId The id of the user who is receiving the notification.
     * @return A list of notifications.
     */
    @GetMapping("/notifications/all/{receiverId}")
    public List<Notification> findNotificationsByReceiverId(@PathVariable(value = "receiverId") int receiverId) {

        return notificationService.findByReceiverId(receiverId);
    }

    /**
     * PUT Method for updating a notification in the database.
     *
     * @param notificationId The id of the notification to be updated.
     * @param notification   The notification object that is to be updated.
     * @return A ResponseEntity<Notification> object is being returned.
     */
    @PutMapping("/notification/update/{notificationId}")
    public ResponseEntity<Notification> updateNotification(@NotNull @PathVariable("notificationId") int notificationId,
                                                           @NotNull @RequestBody Notification notification) {

        return new ResponseEntity<>(notificationService.updateNotification(notification, notificationId), HttpStatus.OK);
    }
}
