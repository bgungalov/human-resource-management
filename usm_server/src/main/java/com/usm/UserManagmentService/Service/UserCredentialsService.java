package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.UserCredentials;
import org.springframework.stereotype.Service;

@Service
public interface UserCredentialsService {

    /**
     * Find a UserCredentials object by its username and password.
     *
     * @param username The username of the user.
     * @param password The password to be used for authentication.
     * @return A UserCredentials object
     */
    UserCredentials findByUsernameAndPassword(String username, String password);

    /**
     * Given a username, return the UserCredentials object that represents that user.
     *
     * @param username The username of the user to load.
     * @return A UserCredentials object.
     */
    UserCredentials loadUserByUsername(String username);
}
