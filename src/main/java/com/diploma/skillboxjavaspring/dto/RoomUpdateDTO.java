package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data for updating a room")
public class RoomUpdateDTO {

    @NotBlank(message = "Name can not be blank")
    @Schema(description = "Room name", example = "Lux")
    private String name;

    @NotNull(message = "Number can not be null")
    @Positive(message = "Number must be greater than zero")
    @Schema(description = "Room number", example = "12")
    private Integer number;

    @NotNull(message = "Price can not be null")
    @Positive(message = "Price must be greater than zero")
    @Schema(description = "Room price", example = "1280")
    private Integer price;

    @NotNull(message = "Max capacity can not be null")
    @Positive(message = "Max capacity must be greater than zero")
    @Schema(
            description = "Maximum number of guests that can stay in the room",
            example = "4"
    )
    private Integer maxCapacity;

    @NotBlank(message = "Description can not be blank")
    @Schema(
            description = "Room description",
            example = "Comfortable room with a double bed"
    )
    private String description;

    @Schema(
            description = "Dates when the room is unavailable",
            example = "[\"2026-09-10\", \"2026-09-11\"]"
    )
    private List<LocalDate> closedDates;
}