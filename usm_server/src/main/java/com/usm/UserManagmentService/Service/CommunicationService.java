package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Attendance;
import com.usm.UserManagmentService.Entity.Communication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunicationService {

    /**
     * "Find the communication for the given attendance, or create a new one if none exists."
     *
     * The first thing we do is get the communication for the given attendance. We do this by calling the
     * `findCommunication` function
     *
     * @param attendance A list of Attendance objects.
     * @return A Communication object.
     */
    Communication findOrCreateCommunication(List<Attendance> attendance);

    /**
     * Get all communications.
     *
     * @return A list of all the communications in the database.
     */
    List<Communication> getAllCommunications();

    /**
     * Finds a communication by its id
     *
     * @param communicationId The id of the communication you want to find.
     * @return Communication
     */
    Communication findCommunicationByCommunicationId(int communicationId);

    /**
     * Deletes the record with the given id.
     *
     * @param id The id of the Communication to be deleted.
     */
    void deleteById(int id);
}
