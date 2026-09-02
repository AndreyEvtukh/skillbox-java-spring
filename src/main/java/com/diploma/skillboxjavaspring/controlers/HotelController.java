package com.diploma.skillboxjavaspring.controlers;

import com.diploma.skillboxjavaspring.dto.HotelRequestDTO;
import com.diploma.skillboxjavaspring.dto.HotelResponseDTO;
import com.diploma.skillboxjavaspring.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.UUID;

/**
 * Exposes REST endpoints for managing hotels.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    /**
     * Retrieves all hotels.
     *
     * @return a collection of hotel response DTOs
     */
    @Operation(
            summary = "Retrieves all hotels",
            description = "Returns a collection of hotels with their available details."
    )
    @GetMapping("/all")
    public Collection<HotelResponseDTO> getAll() {
        return hotelService.getAll();
    }

    /**
     * Retrieves a hotel by its unique identifier.
     *
     * @param ID the unique identifier of the requested hotel
     * @return an {@code OK} response containing the requested hotel
     */
    @Operation(
            summary = "Retrieves a hotel by ID",
            description = "Returns the hotel details for the supplied unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelResponseDTO> getById(
            @Parameter(
                    description = "Unique hotel identifier",
                    required = true,
                    example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
            @PathVariable("id") UUID ID
    ) {
        return ResponseEntity.ok(hotelService.getById(ID));
    }

    /**
     * Creates a new hotel.
     *
     * @param hotelRequestDTO the data for the hotel to create
     * @return a {@code 201 Created} response containing the created hotel
     */
    @Operation(
            summary = "Add new hotel",
            description = "Creates a new hotel and returns the created hotel."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Hotel created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HotelResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "Grand Hotel",
                                        "title": "Grand Hotel — comfortable hotel in the city center",
                                        "city": "Paris",
                                        "address": "10 Rue de Rivoli",
                                        "distance": 2
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
    public ResponseEntity<HotelResponseDTO> create(
            @Valid @RequestBody HotelRequestDTO hotelRequestDTO
    ) {
        log.error("=> create {}", hotelRequestDTO);
        HotelResponseDTO saved = hotelService.create(hotelRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Updates an existing hotel.
     *
     * @param ID              the unique identifier of the hotel to update
     * @param hotelRequestDTO the data containing the hotel's updated values
     * @return an {@code OK} response containing the updated hotel
     */
    @Operation(
            summary = "Update hotel",
            description = "Updates an existing hotel and returns the updated hotel."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect input data"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> update(
            @Parameter(description = "Unique hotel identifier", required = true)
            @PathVariable("id") UUID ID,
            @Valid @RequestBody HotelRequestDTO hotelRequestDTO
    ) {
        HotelResponseDTO updated = hotelService.update(ID, hotelRequestDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a hotel by its unique identifier.
     *
     * @param ID the unique identifier of the hotel to delete
     * @return a {@code 204 No Content} response after deletion
     */
    @Operation(
            summary = "Delete hotel",
            description = "Deletes the hotel with the supplied unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Hotel deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") UUID ID
    ) {
        hotelService.deleteById(ID);
        return ResponseEntity.noContent().build();
    }
}
