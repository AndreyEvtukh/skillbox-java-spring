package com.diploma.skillboxjavaspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Request data required to create a room.
 *
 * <p>The {@code hotelId} identifies the hotel to which a newly created room belongs.
 * The service layer resolves this identifier to the corresponding hotel entity.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data for creating or updating a room")
public class RoomRequestDTO {

    /**
     * Display name of the room.
     */
    @NotBlank(message = "Name can not be blank")
    @Schema(description = "Room name", example = "Lux")
    private String name;

    /**
     * Positive room number within the hotel.
     */
    @NotNull(message = "Number can not be null")
    @Positive(message = "Number must be greater than zero")
    @Schema(description = "Room number", example = "12")
    private Integer number;

    /**
     * Positive price of the room.
     */
    @NotNull(message = "Price can not be null")
    @Positive(message = "Price must be greater than zero")
    @Schema(description = "Room price", example = "1280")
    private Integer price;

    /**
     * Maximum positive number of guests the room can accommodate.
     */
    @NotNull(message = "Max capacity can not be null")
    @Positive(message = "Max capacity must be greater than zero")
    @Schema(description = "Maximum number of guests that can stay in the room", example = "4")
    private Integer maxCapacity;

    /**
     * Description of the room and its features.
     */
    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Room description", example = "Comfortable room with a double bed")
    private String description;

    /**
     * Dates on which the room is unavailable; may be omitted when there are none.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LocalDate> closedDates;

    /**
     * Identifier of the hotel that contains the room.
     */
    @NotNull(message = "Hotel ID can not be null")
    @Schema(description = "Unique identifier of the hotel containing the room", example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
    private UUID hotelId;
}
