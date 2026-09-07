package com.diploma.skillboxjavaspring.exceptions;

public class InvalidLoginEmailException extends RuntimeException {

    public InvalidLoginEmailException(String email) {
        super("No user found with email: " + email);
    }
}