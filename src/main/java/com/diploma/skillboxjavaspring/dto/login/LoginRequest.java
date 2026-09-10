package com.diploma.skillboxjavaspring.dto.login;

/**
 * Credentials submitted for authentication.
 *
 * @param email    the user's email address
 * @param password the user's raw password
 */
public record LoginRequest(
        String email,
        String password
) {
}