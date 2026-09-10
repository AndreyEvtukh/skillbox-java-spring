package com.diploma.skillboxjavaspring.exceptions;

import java.util.UUID;

/**
 * Thrown when a user cannot be found by its unique ID.
 */
public class UserIDNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the user with the specified unique ID.
     *
     * @param ID the unique ID of the user that was not found
     */
    public UserIDNotFoundException(UUID ID) {
        super("User with ID " + ID + " not found.");
    }
}
