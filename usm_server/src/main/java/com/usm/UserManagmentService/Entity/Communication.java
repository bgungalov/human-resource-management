package com.usm.UserManagmentService.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A model class for Communication
 */
@Entity
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communication_id")
    private int communicationId;

    @OneToMany(mappedBy = "communication",
            fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    private Set<Attendance> attendance = new HashSet<>();

    @OneToMany(mappedBy = "communication",
            fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> messages = new LinkedList<>();

    public Communication() {
    }

    public Communication(Set<Attendance> attendance, List<Message> messages) {
        this.attendance = attendance;
        this.messages = messages;
    }

    public Communication(int communicationId, Set<Attendance> attendance, List<Message> messages) {
        this.communicationId = communicationId;
        this.attendance = attendance;
        this.messages = messages;
    }

    public int getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(int communicationId) {
        this.communicationId = communicationId;
    }

    public Set<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(Set<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Communication{" +
                "communicationId=" + communicationId +
                ", attendance=" + attendance +
                ", messages=" + messages +
                '}';
    }
}
