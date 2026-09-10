package com.diploma.skillboxjavaspring.dto.hotels;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains the rating submitted for a hotel.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRatingRequestDTO {

    /**
     * The new rating value assigned to the hotel.
     */
    @NotNull(message = "Distance can not be null")
    @Schema(example = "4")
    private Integer newRating;

}
