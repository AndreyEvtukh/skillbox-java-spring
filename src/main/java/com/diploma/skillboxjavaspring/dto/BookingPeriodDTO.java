package com.diploma.skillboxjavaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a date range during which a room is booked.
 *
 * <p>The {@code from} date is the start of the booking period and {@code to} is its end.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPeriodDTO {

    /**
     * Start date of the booking period.
     */
    private LocalDate from;

    /**
     * End date of the booking period.
     */
    private LocalDate to;
}
