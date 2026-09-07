package com.diploma.skillboxjavaspring.exceptions;

public class InvalidLoginPasswordException extends RuntimeException {

    public InvalidLoginPasswordException() {
        super("Invalid password");
    }
}