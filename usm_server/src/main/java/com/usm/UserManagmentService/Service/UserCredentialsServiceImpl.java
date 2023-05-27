package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserCredentials;
import com.usm.UserManagmentService.Repository.UserCredentialsRepository;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    /**
     * It takes in a username and password, and returns a UserCredentials object.
     *
     * @param username The username of the user
     * @param password The password to be encoded.
     * @return UserCredentials.
     */
    @Override
    public UserCredentials findByUsernameAndPassword(String username, String password) {

        if (username != null && password != null) {
            UserCredentials userCredentials = userCredentialsRepository.findByUsernameAndPassword(username, password);
            if (userCredentials != null && !userCredentials.getUsername().isEmpty() && !userCredentials.getPassword().isEmpty()) {

                return userCredentials;
            }
        }

        throw new ResourceNotFoundException("Wrong username or password!");
    }

    /**
     * Takes a username as a parameter and returns a UserCredentials object.
     *
     * @param username The username of the user to be authenticated.
     * @return UserCredentials.
     */
    @Override
    public UserCredentials loadUserByUsername(String username) {

        if (username != null && !username.isEmpty()) {
            UserCredentials userCredentials = userCredentialsRepository.findByUsername(username);
            if (userCredentials != null && !userCredentials.getUsername().isEmpty() && !userCredentials.getPassword().isEmpty()) {
                return userCredentials;
            }
        }

        throw new ResourceNotFoundException("Wrong user credentials!");
    }
}
