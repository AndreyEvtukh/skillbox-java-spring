package com.diploma.skillboxjavaspring.dto.logout;

/**
 * Contains the credentials needed to log out a user.
 *
 * @param email the email address of the user to log out
 */
public record LogoutRequest(
        String email
) {
}