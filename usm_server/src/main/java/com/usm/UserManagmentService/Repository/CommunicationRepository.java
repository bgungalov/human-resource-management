package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Creating a repository for the Communication entity.
 */
@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Integer> {

    /**
     * "Find the communication between two users by finding the communication that both users are attending."
     * The first query is a sub-query that finds the communication_id of the communication that both users are attending
     *
     * @param attendantOne The user_id of the first user
     * @param attendantTwo the user_id of the second user who is sending the message
     * @return A communication object.
     */
    @Query(value = "SELECT * FROM users.communication" +
            "       WHERE communication_id IN (" +
            "       SELECT communication_id" +
            "        FROM users.attendance" +
            "        WHERE users.attendance.user_id = :attendantOne" +
            "        AND users.attendance.communication_id" +
            "        IN (" +
            "        SELECT communication_id" +
            "        FROM users.attendance" +
            "        WHERE users.attendance.user_id = :attendantTwo));", nativeQuery = true)
    Communication findCommunicationByAttendanceId(@Param("attendantOne") Integer attendantOne, @Param("attendantTwo") Integer attendantTwo);

    /**
     * Finds a communication by its id
     *
     * @param communicationId The id of the communication you want to find.
     * @return Communication
     */
    Communication findCommunicationByCommunicationId(int communicationId);
}
