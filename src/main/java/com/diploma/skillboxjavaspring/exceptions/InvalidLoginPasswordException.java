package com.diploma.skillboxjavaspring.exceptions;

/**
 * Signals that the password supplied during login is invalid.
 */
public class InvalidLoginPasswordException extends RuntimeException {

    /**
     * Creates an exception for an invalid login password.
     */
    public InvalidLoginPasswordException() {
        super("Invalid password");
    }
}