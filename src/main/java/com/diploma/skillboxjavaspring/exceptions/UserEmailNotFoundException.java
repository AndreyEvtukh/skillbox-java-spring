package com.diploma.skillboxjavaspring.exceptions;

/**
 * Thrown when a user cannot be found by its unique email.
 */
public class UserEmailNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the user with the specified identifier (email).
     *
     * @param email the unique identifier of the user that was not found
     */
    public UserEmailNotFoundException(String email) {
        super("User with email " + email + " not found.");
    }
}
