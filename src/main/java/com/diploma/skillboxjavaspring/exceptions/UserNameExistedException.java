package com.diploma.skillboxjavaspring.exceptions;

/**
 * Thrown when an attempt is made to create a user with a username that already exists.
 */
public class UserNameExistedException extends RuntimeException {

    /**
     * Creates an exception for the specified duplicate username.
     *
     * @param username the username that is already associated with a user
     */
    public UserNameExistedException(String username) {
        super("User with name " + username + " already exists.");
    }
}
