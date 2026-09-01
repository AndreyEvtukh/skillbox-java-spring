package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Room data returned by the API.
 *
 * <p>Includes the identifier of the hotel to which the room belongs.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Room response")
public class RoomResponseDTO {

    /**
     * Unique identifier of the room.
     */
    @Schema(description = "Unique room identifier", example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
    private UUID id;

    /**
     * Display name of the room.
     */
    @Schema(description = "Room name", example = "1 room, 2 beds")
    private String name;

    /**
     * Room number within the hotel.
     */
    @Schema(description = "Room number", example = "21")
    private Integer number;

    /**
     * Price of the room.
     */
    @Schema(description = "Room price", example = "1280")
    private BigDecimal price;

    /**
     * Maximum number of guests the room can accommodate.
     */
    @Schema(description = "Maximum number of guests", example = "5")
    private Integer maxCapacity;

    /**
     * Description of the room and its features.
     */
    @Schema(description = "Room description", example = "Some of many comfortable rooms")
    private String description;

    /**
     * Identifier of the hotel that contains the room.
     */
    @Schema(description = "Unique identifier of the hotel", example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
    private UUID hotelId;

    /**
     * Dates on which the room is unavailable; may be omitted when there are none.
     */
    @Schema(description = "List of dates when the room is unavailable", example = "[2021-09-10]")
    private List<LocalDate> closedDates = new ArrayList<>();
}
