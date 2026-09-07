package com.diploma.skillboxjavaspring.dto.hotels;

import java.math.BigDecimal;

/**
 * Contains optional criteria for filtering hotels.
 *
 * @param name          the hotel name to match
 * @param title         the hotel title to match
 * @param city          the city to match
 * @param address       the address to match
 * @param distanceToCenter the maximum distance from the city center
 * @param rating        the minimum hotel rating
 * @param numOfRating   the minimum number of ratings
 */
public record HotelFilterDTO(
        String name,
        String title,
        String city,
        String address,
        BigDecimal distanceToCenter,
        BigDecimal rating,
        Integer numOfRating
) {
}
