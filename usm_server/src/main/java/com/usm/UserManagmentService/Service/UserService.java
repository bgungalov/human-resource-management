package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.User;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserService {

    /**
     * Save a user to the database.
     *
     * @param user The user object to be saved.
     * @return The user object that was saved.
     */
    User saveUser(User user);

    /**
     * This function returns a User object that corresponds to the userId parameter.
     *
     * @param userId The id of the user to get.
     * @return A User object.
     */
    User getUserById(int userId);

    /**
     * Update a user partially, given the user object and the user id.
     *
     * @param user   The user object that contains the updated information.
     * @param userId The id of the user you want to update.
     * @return The updated user.
     */
    User updateUserPartially(User user, int userId);

    /**
     * It returns a Page of User objects, where the page number is selectedPage, the page size is pageSize, the sort order
     * is sortOrder, and the field to sort on is fieldOrder
     *
     * @param selectedPage The page number that the user has selected.
     * @param pageSize     The number of records to be displayed on a page.
     * @param sortOrder    "asc" or "desc"
     * @param fieldOrder   The field to order by.
     * @return A Page<User> object.
     */
    Page<User> findUsersWithPagination(int selectedPage, int pageSize, String sortOrder, String fieldOrder);

    /**
     * It returns a page of users whose name contains the given first, middle and last name.
     *
     * @param firstName    The first name of the user.
     * @param middleName   The middle name of the user.
     * @param lastName     The last name of the user.
     * @param selectedPage The page number you want to retrieve.
     * @param pageSize     The number of records to be displayed in a page.
     * @param sortOrder    "asc" or "desc"
     * @param fieldOrder   The field to order by.
     * @return A Page<User> object.
     */
    Page<User> findByNameContaining(String firstName, String middleName, String lastName, int selectedPage, int pageSize, String sortOrder, String fieldOrder) throws Exception;

    /**
     * Get all existing users.
     */
    List<User> fetchUserList();

    /**
     * Update a user in the database.
     *
     * @param user   The user object that you want to update.
     * @param userId The id of the user you want to update.
     * @return The updated user.
     */
    User updateUser(User user, int userId);

    /**
     * Deletes a user from the database.
     *
     * @param userId The id of the user to delete.
     */
    void deleteUserById(int userId);

    /**
     * Get user by email.
     *
     * @param email find user by email.
     */
    User getUserByEmail(String email);
}
