package com.diploma.skillboxjavaspring.statistics.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Event published when a new user is registered.
 *
 * @param eventId    unique identifier of the event
 * @param eventType  type of the event
 * @param occurredAt date and time when the event occurred
 * @param userId     identifier of the registered user
 */
public record UserRegisteredEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        UUID userId
) {

    public static UserRegisteredEvent create(UUID userId) {
        return new UserRegisteredEvent(
                UUID.randomUUID(),
                "USER_REGISTERED",
                Instant.now(),
                userId
        );
    }
}