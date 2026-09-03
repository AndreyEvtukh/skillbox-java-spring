package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Response data describing a persisted room booking.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    /**
     * Unique identifier assigned to the booking.
     */
    @Schema(
            description = "Unique booking identifier",
            example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969"
    )
    private UUID id;

    /**
     * Identifier of the user who made the booking.
     */
    @Schema(
            description = "Unique user identifier",
            example = "2aa42205-a187-40bd-88d9-e891d713e672"
    )
    private UUID userId;

    /**
     * Identifier of the room reserved by the booking.
     */
    @Schema(
            description = "Unique room identifier",
            example = "f113ca5b-f7df-40a9-89bd-37ade067525b"
    )
    private UUID roomId;

    /**
     * First day of the booked stay.
     */
    @Schema(
            description = "Check-In date",
            example = "2025-08-05"
    )
    private LocalDate checkIn;

    /**
     * First day after the booked stay.
     */
    @Schema(
            description = "Check-Out date",
            example = "2026-01-02"
    )
    private LocalDate checkOut;
}
