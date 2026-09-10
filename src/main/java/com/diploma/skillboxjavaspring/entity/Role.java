package com.diploma.skillboxjavaspring.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Defines the access roles available to application users.
 */
public enum Role {

    /**
     * Standard user role.
     */
    USER,

    /**
     * Administrator role with elevated privileges.
     */
    ADMIN;

    /**
     * Converts a JSON role value to its corresponding enum constant.
     *
     * @param value the role value to convert
     * @return the matching role
     * @throws IllegalArgumentException if the value is {@code null}, contains surrounding spaces,
     *                                  or does not match a role
     */
    @JsonCreator
    public static Role fromValue(String value) {
        if (value == null || !value.equals(value.trim())) {
            throw new IllegalArgumentException("Role must not contain leading or trailing spaces");
        }

        return Role.valueOf(value);
    }
}
