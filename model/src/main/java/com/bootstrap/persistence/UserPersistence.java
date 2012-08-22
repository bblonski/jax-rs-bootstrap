package com.bootstrap.persistence;

import com.bootstrap.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Utility for managing users for the UserService.
 *
 * @author Brian Blonski
 */
@Transactional
public interface UserPersistence extends BasePersistence<User> {
    @Query("select u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);
}