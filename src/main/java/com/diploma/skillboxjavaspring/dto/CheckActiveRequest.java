package com.diploma.skillboxjavaspring.dto;

/**
 * Request containing the email address whose active status should be checked.
 *
 * @param email the user's email address
 */
public record CheckActiveRequest(
        String email
) {
}