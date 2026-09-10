package com.diploma.skillboxjavaspring.exceptions;

/**
 * Thrown when an attempt is made to create a user with an email address that already exists.
 */
public class UserEmailExistedException extends RuntimeException {

    /**
     * Creates an exception for the specified duplicate email address.
     *
     * @param email the email address that is already associated with a user
     */
    public UserEmailExistedException(String email) {
        super("User with email " + email + " already exists.");
    }
}
