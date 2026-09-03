package com.diploma.skillboxjavaspring.exceptions;

/**
 * Thrown when a booking attempt conflicts with an existing reservation for the same room
 * and overlapping date range.
 */
public class RoomAlreadyBookedException extends RuntimeException {

    /**
     * Creates a new exception with a descriptive message about the booking conflict.
     *
     * @param message details of the room booking conflict
     */
    public RoomAlreadyBookedException(String message) {
        super(message);
    }
}