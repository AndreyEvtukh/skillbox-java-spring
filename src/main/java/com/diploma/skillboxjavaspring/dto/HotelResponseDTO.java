package com.diploma.skillboxjavaspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents hotel data returned by the API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDTO {

    /**
     * The hotel's unique identifier.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

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
    @Schema(example = "2.6")
    private BigDecimal distance;

    /**
     * The hotel's rating.
     */
    @NotNull(message = "Rating can not be null")
    @Schema(example = "4.2")
    private BigDecimal rating;

    /**
     * The number of ratings used to calculate the hotel's rating.
     */
    @NotNull(message = "Rating count can not be null")
    @Schema(example = "125")
    private Integer ratingCount;
}
