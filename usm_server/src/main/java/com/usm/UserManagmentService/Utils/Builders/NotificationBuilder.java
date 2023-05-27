package com.usm.UserManagmentService.Utils.Builders;

import com.usm.UserManagmentService.Constants.TYPES_OF_NOTIFICATION;
import com.usm.UserManagmentService.Entity.Notification;

import java.util.Date;

/**
 * It's a builder class that builds a Notification object.
 */
public class NotificationBuilder {

    private Notification notification;

    public NotificationBuilder() {
        reset();
    }

    public NotificationBuilder(Notification notification) {
        reset();
        setId(notification.getId())
                .setSenderId(notification.getSenderId())
                .setSender(notification.getSender())
                .setReceiverId(notification.getReceiverId())
                .setBody(notification.getBody()).setType(notification.getType())
                .setCreatedDate(notification.getCreatedDate())
                .setIsNew(notification.isNew());
    }

    public NotificationBuilder setId(int id) {
        this.notification.setId(id);
        return this;
    }

    public NotificationBuilder setSenderId(int id) {
        this.notification.setSenderId(id);
        return this;
    }

    public NotificationBuilder setSender(String sender) {
        this.notification.setSender(sender);
        return this;
    }

    public NotificationBuilder setReceiverId(int id) {
        this.notification.setReceiverId(id);
        return this;
    }

    public NotificationBuilder setBody(String body) {
        this.notification.setBody(body);
        return this;
    }

    public NotificationBuilder setType(TYPES_OF_NOTIFICATION type) {
        this.notification.setType(type);
        return this;
    }

    public NotificationBuilder setCreatedDate(Date date) {
        this.notification.setCreatedDate(date);
        return this;
    }

    public NotificationBuilder setIsNew(boolean isNew) {
        this.notification.setNew(isNew);
        return this;
    }

    public Notification build() {
        return this.notification;
    }

    public void reset() {
        this.notification = new Notification();
    }
}
