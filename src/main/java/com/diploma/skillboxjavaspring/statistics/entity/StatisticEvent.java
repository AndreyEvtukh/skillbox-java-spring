package com.diploma.skillboxjavaspring.statistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * MongoDB document representing a statistical event.
 *
 * <p>Stores information about user registrations and room bookings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics")
public class StatisticEvent {

    /**
     * MongoDB document identifier.
     */
    @Id
    private String id;

    /**
     * Unique identifier of the event.
     */
    private UUID eventId;

    /**
     * Type of the statistical event.
     */
    private String eventType;

    /**
     * Date and time when the event occurred.
     */
    private Instant occurredAt;

    /**
     * Identifier of the user associated with the event.
     */
    private UUID userId;

    /**
     * Room booking check-in date.
     */
    private LocalDate checkIn;

    /**
     * Room booking check-out date.
     */
    private LocalDate checkOut;
}