package com.diploma.skillboxjavaspring.dto.logout;

/**
 * Contains the result of a user's logout operation.
 *
 * @param email  the email address of the logged-out user
 * @param ok     whether the logout completed successfully
 * @param active whether the user remains active after logout
 */
public record LogoutResponse(
        String email,
        Boolean ok,
        Boolean active
) {}
