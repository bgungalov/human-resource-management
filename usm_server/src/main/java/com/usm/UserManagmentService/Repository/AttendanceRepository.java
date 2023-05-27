package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creating a repository for the Attendance class.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
