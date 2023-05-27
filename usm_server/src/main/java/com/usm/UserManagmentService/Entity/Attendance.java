package com.usm.UserManagmentService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * The Attendance class is a JPA entity that represents a row in the Attendance table.
 */
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int userId;

    private String attendanceUsername;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "communication_id")
    @JsonIgnore
    private Communication communication;

    public Attendance() {
    }

    public Attendance(int userId, String attendanceUsername, Communication communication) {
        this.userId = userId;
        this.attendanceUsername = attendanceUsername;
        this.communication = communication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAttendanceUsername() {
        return attendanceUsername;
    }

    public void setAttendanceUsername(String attendanceUsername) {
        this.attendanceUsername = attendanceUsername;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

//    @Override
//    public String toString() {
//        return "Attendance{" +
//                "id=" + id +
//                ", userId=" + userId +
//                ", attendanceUsername='" + attendanceUsername + '\'' +
//                ", communication=" + communication +
//                '}';
//    }
}
