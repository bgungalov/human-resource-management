package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.NewRoleRequest;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserRoles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserRolesService {

    /**
     * Find all the roles in the database.
     *
     * @return A list of all the roles in the database.
     */
    List<UserRoles> findAllRoles();

    /**
     * Save a role to the database.
     *
     * @param role The role to be saved.
     * @return The role that was saved.
     */
    UserRoles saveRole(UserRoles role);

    /**
     * Find the roles of a user by their id.
     *
     * @param id The id of the user you want to find the roles for.
     * @return Optional<UserRoles>
     */
    Optional<List<UserRoles>> getRolesByUserId(int id);

    /**
     * Deletes a role by id
     *
     * @param id The id of the role to delete.
     */
    void deleteRoleById(int id);

    /**
     * Assigns a role to a user.
     *
     * @param userId The id of the user you want to assign a role to.
     * @param role the role you want to assign to the user.
     * @return The user object with the new role.
     */
    User assignUserRole(int userId, UserRoles role);

    /**
     * Assigns a new role to a user
     *
     * @param newRoleRequest This is a JSON object that contains the following:
     * @return A UserRoles object.
     */
    UserRoles assignNewRole(NewRoleRequest newRoleRequest) throws Exception;

    /**
     * Unassigns a role from a user
     *
     * @param userId The id of the user to unassign the role from.
     * @param roleId The id of the role to assign to the user.
     */
    void unassignUserRole(int userId, int roleId);

    /**
     * Get all the roles for a user.
     *
     * @param user The user object that you want to get the roles for.
     * @return A list of UserRoles objects.
     */
    List<UserRoles> getUserRoles(User user);

    /**
     * Get the active role of the current user.
     *
     * @param userId The userId of the user you want to get the active role for.
     * @return The active role of the current user.
     */
    UserRoles getActiveCurrentUserRole(int userId);

    /**
     * Given a list of user roles, return a list of active roles.
     *
     * @param roles List of UserRoles objects
     * @return A list of UserRoles objects that are active.
     */
    List<UserRoles> extractActiveRolesFromUser(List<UserRoles> roles);

    /**
     * Get all the roles for a user.
     *
     * @param userId The userId of the user whose roles you want to get.
     * @return A list of UserRoles objects.
     */
    List<UserRoles> getAllUserRoles(int userId);

    Optional<UserRoles> findById(int roleId);
}
