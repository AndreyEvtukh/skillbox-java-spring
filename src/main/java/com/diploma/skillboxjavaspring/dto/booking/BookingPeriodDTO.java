package com.diploma.skillboxjavaspring.dto.booking;

import java.time.LocalDate;

/**
 * Represents a date range during which a room is booked.
 *
 * <p>The {@code from} date is the start of the booking period and {@code to} is its end.</p>
 */
public record BookingPeriodDTO() {

    /**
     * Start date of the booking period.
     */
    private static LocalDate from;

    /**
     * End date of the booking period.
     */
    private static LocalDate to;


    /**
     * Constructor
     * @param checkIn
     * @param localDate
     */
    public BookingPeriodDTO(LocalDate checkIn, LocalDate localDate) {
        this();
    }

    public BookingPeriodDTO() {

    }
}
