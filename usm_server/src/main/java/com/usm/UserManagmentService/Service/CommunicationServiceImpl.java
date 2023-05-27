package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Entity.Attendance;
import com.usm.UserManagmentService.Entity.Communication;
import com.usm.UserManagmentService.Exceptions.EmptyVariableException;
import com.usm.UserManagmentService.Repository.AttendanceRepository;
import com.usm.UserManagmentService.Repository.CommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * This class is a Spring service that implements the `CommunicationService` interface
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * Finds or creates a communication between two users
     *
     * @param attendance a list of Attendance objects.
     * @return Communication
     */
    @Override
    public Communication findOrCreateCommunication(List<Attendance> attendance) {

        if (attendance == null || attendance.isEmpty() || attendance.size() < 2) {
            throw new EmptyVariableException("Attendance collection is empty! Please send at least one attendance!");
        }

        List<Integer> attendanceIds = new ArrayList<>();
        attendance.forEach(att -> attendanceIds.add(att.getUserId()));

        Communication currentCommunication = communicationRepository.findCommunicationByAttendanceId(attendanceIds.get(0), attendanceIds.get(1));
        if (currentCommunication != null) {
            return currentCommunication;
        }

        Communication newCommunication = communicationRepository.save(new Communication());

        attendance.forEach(att -> att.setCommunication(newCommunication));

        List<Attendance> newAttendances = attendanceRepository.saveAll(attendance);
        newCommunication.setAttendance(new HashSet<>(newAttendances));

        return newCommunication;
    }

    /**
     * > This function returns a list of all the communications in the database
     *
     * @return A list of all the communications in the database.
     */
    @Override
    public List<Communication> getAllCommunications() {
        return communicationRepository.findAll();
    }

    /**
     * This function finds a communication by its communicationId
     *
     * @param communicationId The id of the communication you want to find.
     * @return Communication
     */
    @Override
    public Communication findCommunicationByCommunicationId(int communicationId) {
        Optional<Communication> communication = Optional.ofNullable(communicationRepository.findCommunicationByCommunicationId(communicationId));

        return communication.orElse(null);
    }

    /**
     * The function deletes a communication by its ID
     *
     * @param id The id of the communication to be deleted.
     */
    @Override
    public void deleteById(int id) {
        communicationRepository.deleteById(id);
        System.out.println("Communication with ID: " + id + " is deleted!");
    }
}
