package com.diploma.skillboxjavaspring.dto.room;

import java.util.List;

/**
 * Paginated response containing rooms matching the requested criteria.
 *
 * @param total total number of matching rooms
 * @param page  zero-based page number
 * @param rooms rooms included in the current page
 */
public record RoomPageResponseDTO(long total, int page, List<RoomResponseDTO> rooms) {
}