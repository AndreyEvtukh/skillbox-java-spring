package com.diploma.skillboxjavaspring.exceptions;

/**
 * Signals that no user exists for a login email address.
 */
public class InvalidLoginEmailException extends RuntimeException {

    /**
     * Creates an exception for the supplied email address.
     *
     * @param email the email address that could not be found
     */
    public InvalidLoginEmailException(String email) {
        super("No user found with email: " + email);
    }
}