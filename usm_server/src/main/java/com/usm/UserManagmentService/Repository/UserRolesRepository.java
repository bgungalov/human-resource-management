package com.usm.UserManagmentService.Repository;

import com.usm.UserManagmentService.Entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Creating a repository for the UserRoles entity.
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {

    /**
     * Find all roles for a user, if any.
     *
     * @param userId The userId of the user you want to find the roles for.
     * @return Optional<List<UserRoles>>
     */
    @Query(value = "SELECT * FROM users.user_roles_details urdt where exists (select * from users.user_role ur where ur.user_id= :userId and ur.role_id = urdt.id);", nativeQuery = true)
    Optional<List<UserRoles>> getRolesByUserId(@Param("userId") int userId);
}
