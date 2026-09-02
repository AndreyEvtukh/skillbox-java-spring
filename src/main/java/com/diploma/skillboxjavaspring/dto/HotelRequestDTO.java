package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Carries the validated data required to create a hotel.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDTO {

    /**
     * The hotel's name.
     */
    @NotBlank(message = "Name can not be blank")
    @Pattern(
            regexp = "^\\S(?:.*\\S)?$",
            message = "Name must not start or end with a space"
    )
    @Schema(example = "Grand Hotel")
    private String name;

    /**
     * A short descriptive title for the hotel.
     */
    @NotBlank(message = "Title can not be blank")
    @Schema(example = "Grand Hotel — comfortable hotel in the city center")
    private String title;

    /**
     * The city where the hotel is located.
     */
    @NotBlank(message = "City can not be blank")
    @Schema(example = "Paris")
    private String city;

    /**
     * The hotel's street address.
     */
    @NotBlank(message = "Address can not be blank")
    @Schema(example = "10 Rue de Rivoli")
    private String address;

    /**
     * The distance to the relevant point of interest.
     */
    @NotNull(message = "Distance can not be null")
    @DecimalMin(value = "0.0", message = "Distance must be greater than or equal to 0")
    @Schema(example = "2.6")
    private BigDecimal distance;
}
