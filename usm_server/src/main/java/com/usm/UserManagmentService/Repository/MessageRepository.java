package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository for the Message entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
