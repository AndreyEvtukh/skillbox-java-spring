package com.diploma.skillboxjavaspring.exeptions;

import java.util.UUID;

/**
 * Thrown when a hotel cannot be found by its unique identifier.
 */
public class HotelNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the hotel with the specified identifier.
     *
     * @param ID the unique identifier of the hotel that was not found
     */
    public HotelNotFoundException(UUID ID) {
        super("Hotel with ID " + ID + " not found.");
    }
}
