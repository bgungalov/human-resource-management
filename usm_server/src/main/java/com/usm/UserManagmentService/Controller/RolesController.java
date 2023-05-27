package com.usm.UserManagmentService.Controller;

import com.usm.UserManagmentService.Entity.NewRoleRequest;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserRoles;
import com.usm.UserManagmentService.Service.UserRolesService;
import com.usm.UserManagmentService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RolesController {

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private UserService userService;

    /***
     * POST Method for creating new role.
     *
     * @param userRole role's name
     * @return newly created role.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<UserRoles> addNewRole(@Validated @RequestBody UserRoles userRole) {
        return new ResponseEntity<>(userRolesService.saveRole(userRole), HttpStatus.OK);
    }

    /**
     * GET Method for finding role by ID.
     *
     * @param id role's id to search for.
     * @return found role.
     */
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<UserRoles>> findRoleById(@PathVariable("id") int id) {
        return new ResponseEntity<>(userRolesService.findById(id), HttpStatus.OK);
    }

    /**
     * GET Method for getting all roles in the database.
     *
     * @return all roles.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<UserRoles>> fetchUserRoles() {
        return new ResponseEntity<>(userRolesService.findAllRoles(), HttpStatus.OK);
    }

    /**
     * PUT Method for updating role by ID.
     *
     * @param userRole new role information.
     * @return newly updated role.
     */
    @PutMapping("/roles/{id}")
    public ResponseEntity<UserRoles> updateRole(@RequestBody UserRoles userRole) {
        return new ResponseEntity<>(userRolesService.saveRole(userRole), HttpStatus.OK);
    }

    /**
     * DELETE Method for deleting role by ID.
     *
     * @param roleId find role by ID and delete it.
     * @return confirmation message.
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteUserRoleById(@PathVariable("id") int roleId) {
        userRolesService.deleteRoleById(roleId);

        return new ResponseEntity<>("Role deleted Successfully!", HttpStatus.OK);
    }

    /**
     * POST Method for assigning role to user
     *
     * @param userId find the user we want to assign role to
     * @param roleId find the role id we want to assign
     * @return ResponseEntity<Object>
     */
    @RequestMapping("/role/assign/{userId}/{roleId}")
    public ResponseEntity<Object> assignUserRole(@PathVariable int userId, @PathVariable int roleId) {
        Optional<UserRoles> foundRoles = userRolesService.findById(roleId);

        userRolesService.assignUserRole(userId, foundRoles.get());

        return new ResponseEntity<>("Role with ID " + roleId + " assigned successfully", HttpStatus.OK);
    }

    /**
     * POST Method for assigning user role by role name
     *
     * @param newRoleRequest the new role that will be
     *                       created and assigned to the user
     * @return ResponseEntity<UserRoles>
     */
    @PostMapping("/assign/role/")
    public ResponseEntity<UserRoles> assignUserRoleByRoleName(@RequestBody NewRoleRequest newRoleRequest) {
        try {
            return new ResponseEntity<>(userRolesService.assignNewRole(newRoleRequest), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST Method for unassigning role from user
     *
     * @param userId find the user we want to unassign role
     * @param roleId find the role id we want to unassign
     * @return ResponseEntity<String>
     */
    @RequestMapping("/role/unassign/{userId}/{roleId}")
    public ResponseEntity<String> unassignUserRole(@PathVariable int userId, @PathVariable int roleId) {
        userRolesService.unassignUserRole(userId, roleId);
        userRolesService.deleteRoleById(roleId);

        return new ResponseEntity<>("User ID: " + userId + " has been unassigned from role with ID: " + roleId, HttpStatus.OK);
    }

    /**
     * GET Method for getting roles by specific user.
     *
     * @param userId current user id to get roles
     * @return user roles.
     */
    @GetMapping("/roles/user")
    public ResponseEntity<List<UserRoles>> getUserRoles(@RequestParam int userId) {
        List<UserRoles> roles = userRolesService.getAllUserRoles(userId);

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
