package com.diploma.skillboxjavaspring.dto.login;

import com.diploma.skillboxjavaspring.entity.Role;

/**
 * Result returned after a successful login.
 *
 * @param username the user's display name
 * @param email    the user's email address
 * @param role     the user's application role
 * @param active   whether the user is currently active
 */
public record LoginResponse(
        String username,
        String email,
        Role role,
        Boolean active
) {}
