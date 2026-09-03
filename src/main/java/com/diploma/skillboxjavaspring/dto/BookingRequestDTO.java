package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Request data required to book a room for a user.
 *
 * <p>Both stay dates must be today or later. The check-out date must also be
 * later than the check-in date; that relationship is validated by the booking service.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data for booking a room")
public class BookingRequestDTO {

    /**
     * Identifies the user making the booking.
     */
    @NotNull(message = "User ID can not be null")
    @Schema(
            description = "Unique user identifier",
            example = "024b8315-2389-4642-af8b-670428790452"
    )
    private UUID userId;

    /**
     * Identifies the room to reserve.
     */
    @NotNull(message = "Room ID can not be null")
    @Schema(
            description = "Unique room identifier",
            example = "407df906-4153-4790-86e0-64e7797bf63b"
    )
    private UUID roomId;

    /**
     * First day of the requested stay; it cannot be in the past.
     */
    @NotNull(message = "Check-in date can not be null")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    @Schema(
            description = "Check-in date",
            example = "2026-09-10"
    )
    private LocalDate checkIn;

    /**
     * First day after the requested stay; it cannot be in the past.
     */
    @NotNull(message = "Check-out date can not be null")
    @FutureOrPresent(message = "Check-out date must be today or in the future")
    @Schema(
            description = "Check-out date",
            example = "2026-10-15"
    )
    private LocalDate checkOut;
}
