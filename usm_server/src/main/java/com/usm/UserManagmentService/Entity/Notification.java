package com.usm.UserManagmentService.Entity;

import com.usm.UserManagmentService.Constants.TYPES_OF_NOTIFICATION;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    int id;
    int senderId;
    String sender;
    int receiverId;
    String body;
    TYPES_OF_NOTIFICATION type;
    Date createdDate;
    boolean isNew;

    public Notification() {
    }

    public Notification(int id, int senderId, String sender, int receiverId, String body, TYPES_OF_NOTIFICATION type, Date createdDate, boolean isNew) {
        this.id = id;
        this.senderId = senderId;
        this.sender = sender;
        this.receiverId = receiverId;
        this.body = body;
        this.type = type;
        this.createdDate = createdDate;
        this.isNew = isNew;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TYPES_OF_NOTIFICATION getType() {
        return type;
    }

    public void setType(TYPES_OF_NOTIFICATION type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", sender='" + sender + '\'' +
                ", receiverId=" + receiverId +
                ", body='" + body + '\'' +
                ", type=" + type +
                ", createdDate=" + createdDate +
                ", isNew=" + isNew +
                '}';
    }
}
