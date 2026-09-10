package com.diploma.skillboxjavaspring.dto;

import lombok.*;

/**
 * Response data returned when processing a request results in an error.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    /**
     * HTTP status code associated with the error.
     */
    private int status;

    /**
     * Human-readable description of the error.
     */
    private String message;
}
