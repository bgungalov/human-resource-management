package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Visit;
import com.usm.UserManagmentService.Entity.VisitStatistic;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface VisitService {

    /**
     * It saves the visitor's information to the database
     *
     * @param visit The visit object to be saved.
     * @return The visit object that was saved.
     */
    Visit saveVisitorInfo(Visit visit);

    /**
     * Fetch a list of all the visitors that have visited the website.
     *
     * @return A list of all the visitors in the database.
     */
    List<Visit> fetchVisitorList();

    /**
     * Find all the user actions for the current user, and return them in a page of size pageSize, starting at the
     * selectedPage.
     *
     * @param selectedPage The page number that the user has selected.
     * @param pageSize     The number of records to be displayed on a page.
     * @return A Page<Visit> object.
     */
    Page<Visit> findUserActionsWithPagination(int selectedPage, int pageSize);

    /**
     * Get the statistics of visits by method type
     *
     * @param methodType The type of method you want to get statistics for.
     * @param startDate  The start date of the time period you want to query.
     * @param endDate    The end date of the time period you want to get statistics for.
     * @return A list of VisitStatistic objects.
     */
    List<VisitStatistic> getStatisticsByMethodType(String methodType, Date startDate, Date endDate) throws Exception;

    /**
     * Search for all actions performed by a user.
     *
     * @param username The username of the user whose actions you want to search for.
     * @return A list of visits.
     */
    List<Visit> searchActionsByUsername(String username) throws Exception;

    /**
     * Find all visits that have a user whose username contains the given username, and return them in a page of size
     * pageSize, starting at the given selectedPage.
     *
     * @param username     the username of the user who created the visit
     * @param selectedPage The page number you want to retrieve.
     * @param pageSize     The number of records to be displayed on a page.
     * @return A Page<Visit> object.
     */
    Page<Visit> findByUserContaining(String username, int selectedPage, int pageSize) throws Exception;

    /**
     * Delete visit by visit ID
     *
     * @param id of deletable visit
     */
    void deleteById(int id);
}
