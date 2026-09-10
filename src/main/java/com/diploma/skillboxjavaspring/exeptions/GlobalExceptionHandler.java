package com.diploma.skillboxjavaspring.exeptions;

import com.diploma.skillboxjavaspring.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates exceptions raised by REST controllers into consistent HTTP error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Converts a missing-hotel exception into a {@code 404 Not Found} response.
     *
     * @param exception the exception raised when the requested hotel does not exist
     * @return an error response containing the exception message
     */
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleHotelNotFound(
            HotelNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    /**
     * Converts a request-validation exception into a {@code 400 Bad Request} response.
     *
     * <p>The response contains the message of the first field validation error, or a
     * default message when no field error is available.</p>
     *
     * @param exception the exception raised when request validation fails
     * @return an error response containing the validation error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Incorrect input data");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        HttpStatus.BAD_REQUEST.value(),
                        message
                ));
    }

    /**
     * Converts an unhandled exception into a {@code 500 Internal Server Error} response.
     *
     * <p>The exception details are deliberately not exposed to the API client.</p>
     *
     * @param exception the unhandled exception
     * @return a generic error response that does not reveal internal details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(
            Exception exception
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal server error"
                ));
    }
}
