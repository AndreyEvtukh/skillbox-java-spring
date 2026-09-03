package com.diploma.skillboxjavaspring.controlers;

import com.diploma.skillboxjavaspring.dto.BookingRequestDTO;
import com.diploma.skillboxjavaspring.dto.BookingResponseDTO;
import com.diploma.skillboxjavaspring.dto.HotelResponseDTO;
import com.diploma.skillboxjavaspring.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * REST controller for viewing and creating room bookings.
 *
 * <p>All endpoints are available under {@code /api/v1/booking}.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Returns every booking recorded in the system.
     *
     * @return booking details for all existing bookings
     */
    @Operation(summary = "Retrieves all booked rooms", description = "Returns a collection of booked rooms with their available details.")
    @GetMapping("/all")
    public Collection<BookingResponseDTO> getAll() {
        return bookingService.getAll();
    }


    /**
     * Creates a booking for a user and a room during the requested date range.
     *
     * <p>The request body is validated before it is passed to the service. On success,
     * the response has status {@code 201 Created}, contains the created booking, and
     * includes its URI in the {@code Location} header.</p>
     *
     * @param bookingRequestDTO user, room, and stay dates for the new booking
     * @return a {@code 201 Created} response containing the created booking
     */
    @Operation(summary = "Create a new booking", description = "Creates a new room booking for the specified user and room, including the check-in and check-out dates.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Booking created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelResponseDTO.class),
                            examples = @ExampleObject(value = """                                    
                                   {
                                       "id": "27117d7b-0a5a-4623-8cf0-eaf9091ad969",
                                       "userId": "2aa42205-a187-40bd-88d9-e891d713e672",
                                       "roomId": "f113ca5b-f7df-40a9-89bd-37ade067525b",
                                       "checkIn": "2025-08-05",
                                       "checkOut": "2026-01-02"
                                   }
                                   """))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect input data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Incorrect input data"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookingResponseDTO> create(
            @Valid @RequestBody BookingRequestDTO bookingRequestDTO
    ) {
        BookingResponseDTO saved = bookingService.create(bookingRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }
}
