package com.usm.UserManagmentService.Controller;

import com.usm.UserManagmentService.Entity.Attendance;
import com.usm.UserManagmentService.Entity.Communication;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    /**
     * HTTP POST METHOD
     * It takes a list of users, creates a list of attendances, and then creates a communication with those attendances
     *
     * @param users A list of users that will be attending the communication.
     * @return Communication object
     */
    @PostMapping("/communication")
    public Communication startCommunication(@NotNull @RequestBody List<User> users) {

        List<Attendance> attendances = new ArrayList<>();

        users.forEach(user -> {
            Attendance attendance = new Attendance(user.getId(), user.getFirstName() + user.getLastName(), new Communication());
            attendances.add(attendance);
        });

        return communicationService.findOrCreateCommunication(attendances);
    }

    /**
     * HTTP GET METHOD
     * This function returns a list of all the communications in the database
     *
     * @return A list of all the communications in the database.
     */
    @GetMapping("/communication/getall")
    public List<Communication> getAllCommunications() {
        return communicationService.getAllCommunications();
    }

    /**
     * HTTP GET METHOD
     * This function is used to get a communication by its communicationId
     *
     * @param communicationId The id of the communication you want to retrieve.
     * @return A communication object
     */
    @GetMapping("/communication/id")
    public ResponseEntity<Communication> getCommunicationByCommunicationId(@RequestParam(value = "communicationId") int communicationId) {
        Communication communication = communicationService.findCommunicationByCommunicationId(communicationId);
        if (communication != null) {
            return new ResponseEntity<>(communication, HttpStatus.OK);
        }

        return new ResponseEntity<>(null,
                HttpStatus.NOT_FOUND);
    }
}
