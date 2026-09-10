package com.diploma.skillboxjavaspring.exceptions;

/**
 * Thrown when a user cannot be found by its unique username.
 */
public class UserNameNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the user with the specified username.
     *
     * @param username the unique username of the user that was not found
     */
    public UserNameNotFoundException(String username) {
        super("User with name " + username + " not found.");
    }
}
