package com.diploma.skillboxjavaspring.exceptions;

public class InvalidHotelRatingException extends RuntimeException {

    public InvalidHotelRatingException() {
        super("Hotel rating must be between 1 and 5");
    }
}
