package com.usm.UserManagmentService.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * This class is a JPA entity that represents the user credentials table in the database.
 */
@Entity
@Table(name = "userCredentials")
public class UserCredentials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    // [a-zA-Z-0-9]
    @NotBlank
    private String username;

    // minimum 6 symbols
    @NotBlank
    private String password;

    private boolean firstTimeLogin;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    User user;

    public UserCredentials() {
    }

    public UserCredentials(String username, String password, boolean firstTimeLogin) {
        this.username = username;
        this.password = password;
        this.firstTimeLogin = firstTimeLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }
}
