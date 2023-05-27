package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.UserCredentials;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Creating a repository for the UserCredentials class.
 */
@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Integer> {

    /**
     * A method that is used to find a user by their username and password.
     */
    UserCredentials findByUsernameAndPassword(String username, String password);

    /**
     * Find a UserCredentials object by its username.
     */
    UserCredentials findByUsername(String username);
}
