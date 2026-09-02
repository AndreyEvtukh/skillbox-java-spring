package com.diploma.skillboxjavaspring.exceptions;

import com.diploma.skillboxjavaspring.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Converts application exceptions into consistent HTTP error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for resources that do not exist.
     *
     * @param exception the exception describing the missing resource
     * @return a response with HTTP 404 status
     */
    @ExceptionHandler({
            HotelNotFoundException.class,
            UserEmailNotFoundException.class,
            UserNameNotFoundException.class,
            UserIDNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            RuntimeException exception
    ) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Handles validation failures in request arguments.
     *
     * @param exception the validation exception
     * @return a response with HTTP 400 status and the first validation error
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

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Handles attempts to create a user whose identifying data already exists.
     *
     * @param exception the exception describing the duplicate user data
     * @return a response with HTTP 400 status
     */
    @ExceptionHandler({
            UserNameExistedException.class,
            UserEmailExistedException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleUserExists(
            RuntimeException exception
    ) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles exceptions that are not covered by a more specific handler.
     *
     * @param exception the unexpected exception
     * @return a response with HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(
            Exception exception
    ) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    /**
     * Builds a standardized error response.
     *
     * @param status the HTTP status to return
     * @param message the error message to include
     * @return the response containing an {@link ErrorResponseDTO}
     */
    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDTO(
                        status.value(),
                        message
                ));
    }
}
