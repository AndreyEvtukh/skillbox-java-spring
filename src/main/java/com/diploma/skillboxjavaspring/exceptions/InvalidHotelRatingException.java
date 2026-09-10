package com.diploma.skillboxjavaspring.exceptions;

/**
 * Signals that a submitted hotel rating is outside the supported range.
 */
public class InvalidHotelRatingException extends RuntimeException {

    /**
     * Creates an exception for a rating that is not between one and five.
     */
    public InvalidHotelRatingException() {
        super("Hotel rating must be between 1 and 5");
    }
}
