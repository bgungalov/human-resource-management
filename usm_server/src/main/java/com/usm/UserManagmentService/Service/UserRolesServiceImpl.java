package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.NewRoleRequest;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserRoles;
import com.usm.UserManagmentService.Repository.UserRepository;
import com.usm.UserManagmentService.Repository.UserRolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.usm.UserManagmentService.Constants.Constants.ROLE_BASIC_USER;

@Service
public class UserRolesServiceImpl implements UserRolesService {

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CalendarService calendarService;

    /**
     * Get all created roles.
     *
     * @return all created roles.
     */
    @Override
    public List<UserRoles> findAllRoles() {
        return userRolesRepository.findAll();
    }

    /**
     * Create a new role.
     *
     * @param role new role data.
     * @return newly created role.
     */
    @Override
    public UserRoles saveRole(UserRoles role) {
        if (role != null || role.getRoleName() != null) {
            return userRolesRepository.save(role);
        }

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Role details are empty!");
    }

    /**
     * Find roles by user id
     *
     * @param userId user id we want to get roles
     * @return user roles
     */
    @Override
    public Optional<List<UserRoles>> getRolesByUserId(int userId) {
        return userRolesRepository.getRolesByUserId(userId);
    }

    /**
     * Delete role by role id.
     *
     * @param id role id we want to delete.
     */
    @Override
    public void deleteRoleById(int id) {
        userRolesRepository.deleteById(id);
    }

    /**
     * Assigning role to user by user id and role id.
     *
     * @param userId current user id to be assigned role.
     * @param role   current role be assigned to user.
     */
    @Override
    public User assignUserRole(int userId, UserRoles role) {
        User user = userRepository.findById(userId).orElse(null);

        List<UserRoles> userRoles;

        if (user.getRoles().isEmpty() || user.getRoles() == null) {
            userRoles = new LinkedList<>() {{
                add(role);
            }};

            userRoles.add(role);
            user.setRoles(userRoles);
        } else {
            user.getRoles().add(role);
        }

        return userRepository.save(user);
    }

    /**
     * Creating the new role and assigning it to the user.
     *
     * @param newRoleRequest new role data request.
     */
    @Override
    public UserRoles assignNewRole(NewRoleRequest newRoleRequest) throws Exception {
        User user = userRepository.findById(newRoleRequest.getUserId()).orElse(null);

        isUserFoundContinue(newRoleRequest, user);
        UserRoles newUserRole = new UserRoles();
        newUserRole.setRoleName(newRoleRequest.getRoleName());

        setNewRoleDates(newRoleRequest, newUserRole);
        user.getRoles().add(newUserRole);
        User userDB = userRepository.save(user);

        return userDB.getRoles().get(userDB.getRoles().size() - 1);
    }

    /**
     * Finding all user roles by user id.
     *
     * @param userId the user id we want to get roles.
     * @return all user roles.
     */
    @Override
    public List<UserRoles> getAllUserRoles(int userId) {
        User user = userRepository.findById(userId).orElse(null);

        return user.getRoles().stream().toList();
    }

    @Override
    public Optional<UserRoles> findById(int roleId) {
        return userRolesRepository.findById(roleId);
    }

    /**
     * Unassigning user role by user id and role id.
     *
     * @param userId user id we want to unassing role.
     * @param roleId role id we want to unassing from user.
     */
    @Override
    public void unassignUserRole(int userId, int roleId) {
        User user = userRepository.findById(userId).orElse(null);
        List<UserRoles> userRoles = user.getRoles();

        userRoles.removeIf(x -> x.getId() == roleId);
        userRepository.save(user);
    }

    /**
     * Finding user roles by given user.
     *
     * @param user data we want to get roles.
     * @return all user roles.
     */
    @Override
    public List<UserRoles> getUserRoles(User user) {
        return user.getRoles();
    }

    /**
     * Get current user role.
     *
     * @param userId user id we want to check role.
     * @return current active user role.
     */
    @Override
    public UserRoles getActiveCurrentUserRole(int userId) {
        AtomicReference<UserRoles> currentRole = new AtomicReference<>(new UserRoles(ROLE_BASIC_USER));

        List<UserRoles> roles = userRolesRepository.getRolesByUserId(userId).orElse(null);
        Date currentDate = calendarService.getCurrentDateHoursToMidnight();

        roles.forEach(eachRole -> {
            if (isRoleActive(eachRole, currentDate)) {
                currentRole.set(eachRole);
            }
        });

        return currentRole.get();
    }

    /**
     * Get active user roles.
     *
     * @param roles list of user roles.
     * @return all active user roles.
     */
    public List<UserRoles> extractActiveRolesFromUser(List<UserRoles> roles) {
        Date currentDate = calendarService.getCurrentDateHoursToMidnight();

        return roles.stream().filter(eachUserRole -> isRoleActive(eachUserRole, currentDate)).toList();
    }

    /**
     * Check if user role is active.
     *
     * @param role        role data we want to check.
     * @param currentDate current date.
     * @return true or false.
     */
    private boolean isRoleActive(UserRoles role, Date currentDate) {
        return currentDate.after(role.getStartDate()) && currentDate.before(role.getEndDate());
    }

    /**
     * Check if user is found.
     *
     * @param newRoleRequest new role that will be assigned to user.
     * @param user           user to assign new role to.
     */
    private void isUserFoundContinue(NewRoleRequest newRoleRequest, User user) throws Exception {
        if (user == null) {
            throw new Exception(String.format("User not found! %s", newRoleRequest.getUserId()));
        }
    }

    /**
     * Creating the role start and end date.
     *
     * @param newRoleRequest the new user role data request.
     * @param newUserRole    new user role data.
     */
    private void setNewRoleDates(NewRoleRequest newRoleRequest, UserRoles newUserRole) throws Exception {
        try {
            newUserRole.setStartDate(calendarService.generateDateFromDDMMYYYY(newRoleRequest.getStartDate()));
            newUserRole.setEndDate(calendarService.generateDateFromDDMMYYYY(newRoleRequest.getEndDate()));
        } catch (Exception e) {
            throw new Exception(String.format("Error: Given Date Format is NOT Readable \nStartDate: [%s] or EndDate: [%s]",
                    newRoleRequest.getStartDate(), newRoleRequest.getEndDate()));
        }
    }

    /**
     * Validating new user role by date.
     *
     * @param newUserRole new user role to be created.
     * @param role        current user role.
     */
    private void validateNewUserRole(UserRoles newUserRole, UserRoles role) {
        if (!role.getEndDate().before(newUserRole.getStartDate())) {
            try {
                throw new Exception(String.format("Error: New role cannot be assigned because there is assigned role for this period:" +
                        "\nStartDate: [%s] or EndDate: [%s]", role.getStartDate(), role.getEndDate()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
