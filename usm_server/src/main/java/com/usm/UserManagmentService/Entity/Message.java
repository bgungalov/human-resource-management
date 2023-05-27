package com.usm.UserManagmentService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * A model class for Message
 */
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private int senderId;
    private int receiverId;
    private String body;
    private boolean isNew;
    private Date updateDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "communication_id")
    @JsonIgnore
    private Communication communication;


    public Message() {
    }

    public Message(int senderId, int receiverId, String body, boolean isNew, Date updateDate, Communication communication) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.body = body;
        this.isNew = isNew;
        this.updateDate = updateDate;
        this.communication = communication;
    }

    public Message(int senderId, int receiverId, String body, boolean isNew, Date updateDate, int messageId, Communication communication) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.body = body;
        this.isNew = isNew;
        this.updateDate = updateDate;
        this.messageId = messageId;
        this.communication = communication;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }
}
