package com.diploma.skillboxjavaspring.exceptions;

import java.util.UUID;

/**
 * Thrown when a room cannot be found by its unique identifier.
 */
public class RoomNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the room with the specified identifier.
     *
     * @param ID the unique identifier of the room that was not found
     */
    public RoomNotFoundException(UUID ID) {
        super("Room with ID " + ID + " not found.");
    }
}
