package com.diploma.skillboxjavaspring.repositories;

import com.diploma.skillboxjavaspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides persistence operations and lookup queries for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by username.
     *
     * @param attr0 the username to search for
     * @return the matching user, if present
     */
    Optional<User> findByUsername(String attr0);

    /**
     * Determines whether a user exists with the specified username.
     *
     * @param username the username to check
     * @return {@code true} if the username is already in use
     */
    boolean existsByUsername(String username);

    /**
     * Determines whether a user exists with the specified email address.
     *
     * @param email the email address to check
     * @return {@code true} if the email address is already in use
     */
    boolean existsByEmail(String email);
}
