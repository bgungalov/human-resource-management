package com.usm.UserManagmentService.Controller;

import com.usm.UserManagmentService.Entity.Visit;
import com.usm.UserManagmentService.Entity.VisitStatistic;
import com.usm.UserManagmentService.Service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class VisitStatisticsController {

    @Autowired
    private VisitService visitService;

    /**
     * GET Method for viewing user actions statistic.
     *
     * @param methodType the method type (get, post...).
     * @param startDate  the start date.
     * @param endDate    the end date.
     * @return all statistics by given parameters.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/statistics")
    public ResponseEntity<List<VisitStatistic>> getStatistics(@RequestParam String methodType,
                                                              @RequestParam Date startDate,
                                                              @RequestParam Date endDate) {

        try {
            return new ResponseEntity<>(visitService.getStatisticsByMethodType(methodType, startDate, endDate),
                    HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET Method for searching actions by username.
     *
     * @param username     current username.
     * @param selectedPage current selected page.
     * @param pageSize     current page size.
     * @return found actions.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/action-username/{selectedPage}/{pageSize}")
    public ResponseEntity<Page<Visit>> searchActionsByUsername(@RequestParam String username,
                                                               @PathVariable("selectedPage") int selectedPage,
                                                               @PathVariable("pageSize") int pageSize) {
        try {
            return new ResponseEntity<Page<Visit>>(visitService.findByUserContaining(username, selectedPage, pageSize),
                    HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
