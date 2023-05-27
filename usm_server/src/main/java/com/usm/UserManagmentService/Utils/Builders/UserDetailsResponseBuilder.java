package com.usm.UserManagmentService.Utils.Builders;

import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserDetails;
import com.usm.UserManagmentService.Entity.UserDetailsResponse;
import com.usm.UserManagmentService.Entity.UserRoles;

import java.util.List;

/**
 * It's a builder class that builds a UserDetailsResponse object.
 */
public class UserDetailsResponseBuilder {

    private UserDetailsResponse userDetailsResponse;

    public UserDetailsResponseBuilder() {
        reset();
    }

    public UserDetailsResponseBuilder(User user) {
        reset();
        setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMiddleName(user.getMiddleName())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setUserDetails(user.getUserDetails())
                .setUserRoles(user.getRoles());
    }

    public UserDetailsResponseBuilder setId(int id) {
        this.userDetailsResponse.setId(id);
        return this;
    }

    public UserDetailsResponseBuilder setFirstName(String firstName) {
        this.userDetailsResponse.setFirstName(firstName);
        return this;
    }

    public UserDetailsResponseBuilder setMiddleName(String middleName) {
        this.userDetailsResponse.setMiddleName(middleName);
        return this;
    }

    public UserDetailsResponseBuilder setLastName(String lastName) {
        this.userDetailsResponse.setLastName(lastName);
        return this;
    }

    public UserDetailsResponseBuilder setEmail(String email) {
        this.userDetailsResponse.setEmail(email);
        return this;
    }

    public UserDetailsResponseBuilder setPhoneNumber(String phoneNumber) {
        this.userDetailsResponse.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserDetailsResponseBuilder setUserDetails(UserDetails userDetails) {
        this.userDetailsResponse.setUserDetails(userDetails);
        return this;
    }

    public UserDetailsResponseBuilder setUserRoles(List<UserRoles> roles) {
        this.userDetailsResponse.setRoles(roles);
        return this;
    }

    public UserDetailsResponse build() {
        return this.userDetailsResponse;
    }

    public void reset() {
        this.userDetailsResponse = new UserDetailsResponse();
    }
}
