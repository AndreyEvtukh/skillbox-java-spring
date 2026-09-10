package com.diploma.skillboxjavaspring.dto;

import com.diploma.skillboxjavaspring.entity.Role;

/**
 * Contains the result of a user's active-status check.
 *
 * @param email  the user's email address
 * @param active whether the user is currently active
 * @param role   the user's role
 * @param ok     whether the status check completed successfully
 */
public record CheckActiveResponse(
        String email,
        Boolean active,
        Role role,
        Boolean ok
) {
}