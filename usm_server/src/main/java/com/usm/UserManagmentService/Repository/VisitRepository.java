package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Creating a repository for the Visit entity.
 */
public interface  VisitRepository extends JpaRepository<Visit, Integer> {

    /**
     * Find all visits that have a logged time equal to the given time, and return them in a pageable format.
     *
     * @param loggedTime The time the visit was logged.
     * @param pageable   The Pageable object that contains the page number, page size, and sort order.
     * @return A Page of Visits
     */
    Page<Visit> findByLoggedTime(LocalDateTime loggedTime, Pageable pageable);

    /**
     * Find all the visits that have a method of "GET" and a logged time between the start and end date
     *
     * @param method    The method name that you want to search for.
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return List of Visit objects
     */
    @Query(value = "SELECT * FROM users.user_action where method = ?1 and (logged_time between ?2 and ?3)", nativeQuery = true)
    List<Visit> findByMethodEndDate(String method, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * This function searches the database for all actions that have a username that contains the string passed in as a
     * parameter
     *
     * @param username The username of the user you want to search for.
     * @return A list of Visit objects
     */
    @Query(value = "SELECT * FROM users.user_action where user LIKE %?1%", nativeQuery = true)
    List<Visit> searchActionsByUsername(String username);

    /**
     * Find all visits that have a user whose username contains the given string, and return them in a pageable format.
     *
     * @param username The username to search for.
     * @param pageable This is an object that contains the page number, page size, and sort order.
     * @return A Page of Visits
     */
    Page<Visit> findByUserContaining(String username, Pageable pageable);
}
