package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    /**
     * Given an email address, return the user associated with that email address.
     *
     * @param email The email address of the user to retrieve.
     * @return A User object
     */
    User getUserByEmail(String email);

    /**
     * "Find all users where the first name, middle name, or last name is like the given first name, middle name, or last
     * name."
     *
     * @param firstName  The first name of the user.
     * @param middleName The middle name of the user.
     * @param lastName   The last name of the user.
     * @param pageable   This is the Pageable object that contains the page number, page size, and sort order.
     * @return Page<User>
     */
    @Query(value = "SELECT * FROM users.user WHERE" +
            "((:firstName IS NOT NULL AND first_name LIKE :firstName)" +
            "OR (:middleName IS NOT NULL AND middle_name LIKE :middleName )" +
            "OR (:lastName IS NOT NULL AND last_name LIKE :lastName))", nativeQuery = true)
    Page<User> findAllByFirstNameOrMiddleNameOrLastName(@Param("firstName") String firstName,
                                                        @Param("middleName") String middleName,
                                                        @Param("lastName") String lastName,
                                                        Pageable pageable);
}
