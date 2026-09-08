package com.diploma.skillboxjavaspring.dto.hotels;

import java.util.List;

/**
 * Contains a paginated list of hotels and the total number of matching hotels.
 */
public record HotelPageResponseDTO(long total, int page, List<HotelResponseDTO> hotels) {
}
