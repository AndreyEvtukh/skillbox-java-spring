package com.diploma.skillboxjavaspring.statistics.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Event published when a room is booked.
 *
 * @param eventId    unique identifier of the event
 * @param eventType  type of the event
 * @param occurredAt date and time when the event occurred
 * @param userId     identifier of the user who booked the room
 * @param checkIn    room booking check-in date
 * @param checkOut   room booking check-out date
 */
public record RoomBookedEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        UUID userId,
        LocalDate checkIn,
        LocalDate checkOut
) {

    /**
     * Creates a room booking event.
     *
     * @param userId   identifier of the user who booked the room
     * @param checkIn  room booking check-in date
     * @param checkOut room booking check-out date
     * @return a new room booking event
     */
    public static RoomBookedEvent create(
            UUID userId,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        return new RoomBookedEvent(
                UUID.randomUUID(),
                "ROOM_BOOKED",
                Instant.now(),
                userId,
                checkIn,
                checkOut
        );
    }
}