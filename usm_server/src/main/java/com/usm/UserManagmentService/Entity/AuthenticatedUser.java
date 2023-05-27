package com.usm.UserManagmentService.Entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model class for authenticated user details.
 */
public class AuthenticatedUser {

    int id;
    String username;
    List<GrantedAuthority> userRoles = new ArrayList<>();
    String firstName;

    String middleName;

    String lastName;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(int id, String username, List<GrantedAuthority> userRoles, String firstName, String middleName, String lastName) {
        this.id = id;
        this.username = username;
        this.userRoles = userRoles;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GrantedAuthority> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<GrantedAuthority> userRoles) {
        this.userRoles = userRoles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
