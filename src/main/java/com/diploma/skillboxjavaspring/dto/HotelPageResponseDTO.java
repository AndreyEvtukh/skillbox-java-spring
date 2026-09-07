package com.diploma.skillboxjavaspring.dto;

import com.diploma.skillboxjavaspring.dto.hotels.HotelResponseDTO;

import java.util.List;

/**
 * Contains a paginated list of hotels and the total number of matching hotels.
 */
public record HotelPageResponseDTO(long total, List<HotelResponseDTO> hotels) {
}
