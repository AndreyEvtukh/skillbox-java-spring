package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRatingRequestDTO {

    @NotNull(message = "Distance can not be null")
    @Schema(example = "4")
    private Integer newRating;

}
