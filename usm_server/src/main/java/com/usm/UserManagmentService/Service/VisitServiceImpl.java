package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Visit;
import com.usm.UserManagmentService.Entity.VisitStatistic;
import com.usm.UserManagmentService.Repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private VisitRepository visitRepository;

    /**
     * Save the visit object to the database.
     *
     * @param visit The Visit object that is passed in from the controller.
     * @return The visit object is being returned.
     */
    public Visit saveVisitorInfo(Visit visit) {
        return visitRepository.save(visit);
    }

    /**
     * Return a list of all visits in the database.
     *
     * @return A list of all the visits in the database.
     */
    public List<Visit> fetchVisitorList() {
        return visitRepository.findAll();
    }

    /**
     * Returns a page of visits, sorted by loggedTime in descending order
     *
     * @param selectedPage The page number you want to display.
     * @param pageSize     The number of records to be displayed on a page.
     * @return A Page object is being returned.
     */
    @Override
    public Page<Visit> findUserActionsWithPagination(int selectedPage, int pageSize) {

        return visitRepository.findAll((PageRequest.of(selectedPage, pageSize, Sort.by("loggedTime").descending())));
    }

    /**
     * It takes a method type, start date and end date as parameters, and returns a list of VisitStatistic objects, which
     * contain the page name, method type and count of visits for each page.
     *
     * @param methodType The type of HTTP method to filter by.
     * @param startDate  The start date of the period to get statistics for.
     * @param endDate    The end date of the period for which you want to get the statistics.
     * @return A list of VisitStatistic objects.
     */
    @Override
    public List<VisitStatistic> getStatisticsByMethodType(String methodType, Date startDate, Date endDate) throws Exception {

        if (methodType.length() == 0) {
            throw new IllegalArgumentException("Method type cannot be empty");
        }

        List<Visit> visits = visitRepository.findByMethodEndDate(methodType.toUpperCase(), calendarService.dateToLocalDateTime(startDate),
                calendarService.dateToLocalDateTime(calendarService.setLastHourOfDay(endDate)));
        Map<String, VisitStatistic> statistics = new HashMap<>();

        visits.forEach(visit -> {
            String pageName = stripSuffix(visit.getPage());
            if (statistics.containsKey(pageName)) {
                int currentCount = statistics.get(pageName).getCount();
                statistics.get(pageName).setCount(currentCount + 1);
            } else {
                statistics.put(pageName, new VisitStatistic(pageName, visit.getMethod(), 1));
            }
        });

        return new ArrayList<>(statistics.values());
    }

    /**
     * It searches for visits by username, and if the username is longer than 2 characters and doesn't contain only spaces,
     * it returns the visits.
     *
     * @param username The username of the user who performed the action.
     * @return A list of visits.
     */
    @Override
    public List<Visit> searchActionsByUsername(String username) throws Exception {

        String possibleSearchParam = username.replaceAll("\\s", "");

        if (username.length() > 2 && !possibleSearchParam.isEmpty()) {
            return visitRepository.searchActionsByUsername(username);
        }

        throw new Exception("Minimum length is 3 and can't contain only spaces");
    }

    /**
     * Find all visits by a user ny given username.
     *
     * @param username     the username of the user you want to search for.
     * @param selectedPage The page number you want to retrieve.
     * @param pageSize     The number of items to be displayed on a page.
     * @return Page<Visit>
     */
    @Override
    public Page<Visit> findByUserContaining(String username, int selectedPage, int pageSize) throws Exception {

        String possibleSearchParam = username.replaceAll("\\s", "");

        if (username.length() > 2 && !possibleSearchParam.isEmpty()) {

            return visitRepository.findByUserContaining(username, PageRequest.of(selectedPage, pageSize));
        }

        throw new Exception("Minimum length is 3 and can't contain only spaces");
    }

    @Override
    public void deleteById(int id) {
        visitRepository.deleteById(id);
    }

    /**
     * It takes a string, and removes the suffix from it.
     *
     * @param page The name of the page to be stripped of the suffix.
     * @return The page name without the suffix.
     */
    private String stripSuffix(String page) {
        Pattern pattern = Pattern.compile("[/](\\d+)");

        return pattern.matcher(page).replaceAll("");
    }
}
