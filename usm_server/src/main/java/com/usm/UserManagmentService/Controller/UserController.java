package com.usm.UserManagmentService.Controller;

import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserDetailsResponse;
import com.usm.UserManagmentService.Service.CalendarService;
import com.usm.UserManagmentService.Service.UserRolesService;
import com.usm.UserManagmentService.Service.UserService;
import com.usm.UserManagmentService.Utils.Builders.UserDetailsResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private CalendarService calendarService;

    /**
     * POST Method for saving new user.
     *
     * @param user new user that will be saved to the database.
     * @return newly saved user.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(
            @Validated @RequestBody User user) {

        User userDB = userService.saveUser(user);

        return new ResponseEntity<>(userDB, HttpStatus.OK);
    }

    /**
     * GET Method for finding user by ID.
     *
     * @param id user's id to search for.
     * @return found user.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDetailsResponse> findById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        UserDetailsResponse userDetailsResponse;
        if (user != null) {
            userDetailsResponse = new UserDetailsResponseBuilder(user).build();
            return new ResponseEntity<>(userDetailsResponse,
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND);
    }

    /**
     * PATCH Method for updating user information partially.
     *
     * @param userId      find user to update by ID.
     * @param userDetails new user details to be saved.
     * @return updated user.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER')")
    @PatchMapping(value = "users/{id}", consumes = "application/json")
    public ResponseEntity<User> updateUserPartially(@PathVariable("id") int userId, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUserPartially(userDetails, userId));
    }

    /**
     * GET Method for showing users in pages and page size.
     *
     * @param selectedPage page to show.
     * @param pageSize     size of page to show.
     * @return selected page with selected page size.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER', 'ROLE_BASIC_USER')")
    @RequestMapping(value = "pagination/users/{selectedPage}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> findUsersWithPagination(@PathVariable("selectedPage") int selectedPage,
                                                              @PathVariable("pageSize") int pageSize,
                                                              @RequestParam(defaultValue = "desc") String sortOrder,
                                                              @RequestParam(defaultValue = "id") String field) {

        Page<User> currentUserPage = userService.findUsersWithPagination(selectedPage, pageSize, sortOrder, field);

        return new ResponseEntity<>(currentUserPage, HttpStatus.OK);
    }

    /**
     * GET Method for searching user by given request parameters and pagination.
     *
     * @param firstName    current first name.
     * @param lastName     current last name.
     * @param middleName   current middle name.
     * @param selectedPage current selected page.
     * @param pageSize     current page size.
     * @param sortOrder    current sort order.
     * @param field        current field to sort by.
     * @return found user with pagination.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER', 'ROLE_BASIC_USER')")
    @RequestMapping(value = "pagination/search/users/{selectedPage}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Object> searchUsersByNames(@RequestParam(defaultValue = " ") String firstName,
                                                     @RequestParam(defaultValue = " ") String lastName,
                                                     @RequestParam(defaultValue = " ") String middleName,
                                                     @PathVariable("selectedPage") int selectedPage,
                                                     @PathVariable("pageSize") int pageSize,
                                                     @RequestParam(defaultValue = "desc") String sortOrder,
                                                     @RequestParam(defaultValue = "id") String field) {
        try {
            return new ResponseEntity<>(userService.findByNameContaining(firstName, lastName, middleName, selectedPage, pageSize, sortOrder, field),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * GET Method for getting all users in the database.
     *
     * @return all users.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER', 'ROLE_BASIC_USER')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchUserList() {
        return new ResponseEntity<>(userService.fetchUserList(),
                HttpStatus.OK);
    }

    /**
     * PUT Method for updating user by ID.
     *
     * @param user   new user information.
     * @param userId find user by ID and update the information.
     * @return newly updated user.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADMIN_HELPER')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int userId) {
        return new ResponseEntity<>(userService.updateUser(user, userId),
                HttpStatus.OK);
    }

    /**
     * DELETE Method for deleting user by ID.
     *
     * @param userId find user by ID and delete it.
     * @return confirmation message.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") int userId) {
        userService.deleteUserById(userId);

        return new ResponseEntity<>("Deleted Successfully!",
                HttpStatus.OK);
    }
}
