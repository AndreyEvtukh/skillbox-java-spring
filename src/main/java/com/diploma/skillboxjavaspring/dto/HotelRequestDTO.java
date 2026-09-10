package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDTO {

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
    @DecimalMin(value = "0.0", message = "Distance must be greater than or equal to 0")
    @Schema(example = "2.6")
    private BigDecimal distance;
}