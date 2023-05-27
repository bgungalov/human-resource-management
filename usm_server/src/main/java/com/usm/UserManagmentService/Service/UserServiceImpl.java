package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserDetails;
import com.usm.UserManagmentService.Entity.UserDetailsBuilder;
import com.usm.UserManagmentService.Entity.UserRoles;
import com.usm.UserManagmentService.Repository.UserRepository;

import com.usm.UserManagmentService.Repository.UserRolesRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import java.util.*;

import static com.usm.UserManagmentService.Constants.Constants.ROLE_BASIC_USER;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private CalendarService calendarService;

    private final UserDetailsBuilder userDetailsBuilder = new UserDetailsBuilder();

    /**
     * Calls user repository to save new user.
     *
     * @param user new user that will be saved to the database.
     * @return newly saved user instance with given id.
     */
    @Override
    public User saveUser(User user) {

        checkUserCredentials(user);

        User userToSave = saveNewUser(user);

        if (userToSave != null) {
            return userToSave;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email exists");
    }


    /**
     * Calls user repository to get all users.
     *
     * @return all users.
     */
    @Override
    public List<User> fetchUserList() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Calls user repository to get specific user.
     *
     * @param userId the id of the user we want to retrieve.
     * @return the specific user.
     */
    @Override
    public User getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    /**
     * Call user repository to partially update specific user.
     *
     * @param userDetails new user details to be saved.
     * @param userId      find and update user by id.
     * @return newly saved user with updated details.
     */
    @Override
    public User updateUserPartially(User userDetails, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        return userRepository.save(user);
    }

    /**
     * Pagination request
     * Call user repository to measure the size of the list of searched users.
     *
     * @param selectedPage current selected page.
     * @param pageSize     current page size.
     * @return the required number of page and how many users per page.
     */
    @Override
    public Page<User> findUsersWithPagination(int selectedPage, int pageSize, String sortOrder, String fieldOrder) {

        return userRepository.findAll((PageRequest.of(selectedPage, pageSize, Sort.by(getSortDescription(sortOrder, fieldOrder)))));
    }

    /**
     * Search for user by given parameters.
     * It takes in a first name, middle name, last name, selected page, page size, sort order, and field order and returns
     * a page of users.
     *
     * @param firstName    The first name of the user.
     * @param middleName   The middle name of the user.
     * @param lastName     The last name of the user.
     * @param selectedPage The page number you want to retrieve.
     * @param pageSize     The number of records to return per page.
     * @param sortOrder    ASC or DESC.
     * @param fieldOrder   The field to sort by.
     * @return Page<User>
     */
    @Override
    public Page<User> findByNameContaining(String firstName, String middleName, String lastName,
                                           int selectedPage, int pageSize, String sortOrder, String fieldOrder) throws Exception {

        String possibleSearchParam = String.format("%s%s%s", firstName, middleName, lastName).replaceAll("\\s", "");

        if ((firstName.length() > 2 && !possibleSearchParam.isEmpty()) ||
                (middleName.length() > 2 && !possibleSearchParam.isEmpty()) ||
                (lastName.length() > 2 && !possibleSearchParam.isEmpty())) {

            return userRepository.findAllByFirstNameOrMiddleNameOrLastName(firstName, lastName, middleName,
                    PageRequest.of(selectedPage, pageSize, Sort.by(getSortDescription(sortOrder, fieldOrder))));
        }

        throw new Exception("Minimum length is 3 and can't contain only spaces");
    }


    /**
     * Call user repository to update existing user.
     *
     * @param user   нew user information we want to update.
     * @param userId find the user by ID we want to update.
     * @return the user with the updated information.
     */
    @Override
    public User updateUser(User user, int userId) {
        User userDB = userRepository.findById(userId).get();

        if (Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getFirstName())) {
            userDB.setFirstName(user.getFirstName());
        }

        if (Objects.nonNull(user.getMiddleName()) && !"".equalsIgnoreCase(user.getMiddleName())) {
            userDB.setMiddleName(user.getMiddleName());
        }

        if (Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())) {
            userDB.setLastName(user.getLastName());
        }

        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            userDB.setEmail(user.getEmail());
        }

        if (Objects.nonNull(user.getPhoneNumber()) && !"".equalsIgnoreCase(user.getPhoneNumber())) {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }

        if (Objects.nonNull(user.getUserDetails())) {
            UserDetails userDetails = user.getUserDetails();

            userDB.setUserDetails(
                    this.userDetailsBuilder
                            .setId(userDB.getUserDetails().getId())
                            .setUcn(userDetails.getUcn())
                            .setAddress(userDetails.getAddress())
                            .setCountry(userDetails.getCountry())
                            .setCity(userDetails.getCity())
                            .setCityPostCode(userDetails.getCityPostCode())
                            .setStreet(userDetails.getStreet())
                            .build());
        }

        return userRepository.save(userDB);
    }

    /**
     * Call user repository to delete existing user.
     *
     * @param userId this is the id ot the user we want to delete.
     */
    @Override
    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Gets a user by email.
     *
     * @param email The email of the user you want to retrieve.
     * @return User.
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    /**
     * It takes in a sort order and a field, and returns a list of Sort.Order objects
     *
     * @param sortOrder The order in which the results should be sorted.
     * @param field     The field to sort by.
     * @return A list of Sort.Order objects.
     */
    private List<Sort.Order> getSortDescription(String sortOrder, String field) {
        String currentOrder = "desc";
        String currentField = "id";

        if (sortOrder != null || !sortOrder.isEmpty()) {
            currentOrder = sortOrder;
        }
        if (field != null || !field.isEmpty()) {
            currentField = field;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        switch (currentOrder.toLowerCase()) {
            case "desc" -> sorts.add(Sort.Order.desc(currentField));
            case "asc" -> sorts.add(Sort.Order.asc(currentField));
            default -> sorts.add(Sort.Order.desc(currentField));
        }

        return sorts;
    }

    /**
     * If the user is not already in the database, and the username and password are not null, then save the user to the
     * database
     *
     * @param user the user object that is being saved
     * @return A new user is being returned.
     */
    private User saveNewUser(User user) {

        if (userRepository.getUserByEmail(user.getEmail()) == null
                && user.getUserCredentials().getUsername() != null
                && user.getUserCredentials().getPassword() != null) {

            int year = Calendar.getInstance().get(Calendar.YEAR);
            Date today = calendarService.getCurrentDateHoursToMidnight();

            user.setRoles(new LinkedList<>() {
                {
                    add(new UserRoles(ROLE_BASIC_USER, today,
                            calendarService.setDateToLastDayOfYear(year)));
                }
            });

            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password absent!");
    }

    /**
     * If the user credentials are null, throw a bad request exception.
     *
     * @param user The user object that is being created.
     */
    private static void checkUserCredentials(User user) {
        if (user.getUserCredentials() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User credentials missing!");
        }
    }
}