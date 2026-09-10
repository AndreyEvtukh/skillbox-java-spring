package com.diploma.skillboxjavaspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Name can not be blank")
    @Schema(example = "Grand Hotel")
    private String name;

    @NotBlank(message = "Title can not be blank")
    @Schema(example = "Grand Hotel — comfortable hotel in the city center")
    private String title;

    @NotBlank(message = "City can not be blank")
    @Schema(example = "Paris")
    private String city;

    @NotBlank(message = "Address can not be blank")
    @Schema(example = "10 Rue de Rivoli")
    private String address;

    @NotNull(message = "Distance can not be null")
    @Schema(example = "2.6")
    private BigDecimal distance;

    @NotNull(message = "Rating can not be null")
    @Schema(example = "4.2")
    private BigDecimal rating;

    @NotNull(message = "Rating count can not be null")
    @Schema(example = "125")
    private Integer ratingCount;
}