package com.diploma.skillboxjavaspring.dto.room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Optional criteria used to filter available rooms.
 *
 * @param name         room name
 * @param number       room number
 * @param minPrice     minimum room price
 * @param maxPrice     maximum room price
 * @param maxCapacity  minimum required room capacity
 * @param hotelIds     identifiers of hotels whose rooms may be returned
 * @param bookingDates two dates defining the requested booking period
 */
public record RoomFilterDTO(
        String name,
        Integer number,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Integer maxCapacity,
        List<UUID> hotelIds,
        List<LocalDate> bookingDates
) {
}
