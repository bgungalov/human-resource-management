package com.usm.UserManagmentService.Entity;

import java.util.List;

/**
 * It's a class that contains all the information about a user.
 */
public class UserDetailsResponse {

    private int id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    UserDetails userDetails;

    List<UserRoles> roles;

    public UserDetailsResponse() {
    }

    public UserDetailsResponse(int id, String firstName, String middleName, String lastName, String email, String phoneNumber, UserDetails userDetails, List<UserRoles> roles) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userDetails = userDetails;
        this.roles = roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }
}
