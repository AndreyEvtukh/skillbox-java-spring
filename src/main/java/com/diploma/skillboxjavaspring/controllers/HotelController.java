package com.diploma.skillboxjavaspring.controllers;

import com.diploma.skillboxjavaspring.dto.hotels.HotelPageResponseDTO;
import com.diploma.skillboxjavaspring.dto.hotels.*;
import com.diploma.skillboxjavaspring.exceptions.HotelNotFoundException;
import com.diploma.skillboxjavaspring.exceptions.InvalidHotelRatingException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
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

    /**
     * Service that implements hotel-management operations.
     */
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<HotelResponseDTO> create(
            @Valid @RequestBody HotelRequestDTO hotelRequestDTO
    ) {
        log.debug("=> create {}", hotelRequestDTO);
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") UUID ID
    ) {
        hotelService.deleteById(ID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a hotel's rating.
     *
     * @param ID                    the unique identifier of the hotel to update
     * @param hotelRatingRequestDTO the data containing the new hotel rating
     * @return an {@code OK} response containing the updated hotel
     * @throws HotelNotFoundException      if no hotel exists with the specified identifier
     * @throws InvalidHotelRatingException if the rating is outside the supported range
     */
    @Operation(
            summary = "Update hotel rating",
            description = "Updates an existing hotel rating and returns the updated hotel."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel rating updated successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect input data"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PutMapping("/{id}/rating")
    ResponseEntity<HotelResponseDTO> updateRating(
            @Parameter(description = "Unique hotel identifier", required = true)
            @PathVariable("id") UUID ID,
            @Valid @RequestBody HotelRatingRequestDTO hotelRatingRequestDTO
    ) {
        HotelResponseDTO updated = hotelService.updateRating(ID, hotelRatingRequestDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated and sorted collection of hotels matching the supplied filters.
     *
     * @param name             optional hotel name filter
     * @param title            optional hotel title filter
     * @param city             optional city filter
     * @param address          optional address filter
     * @param distanceToCenter optional maximum distance from the city center
     * @param rating           optional minimum hotel rating
     * @param numOfRating      optional minimum number of ratings
     * @param page             zero-based page number
     * @param size             number of hotels per page
     * @param sort             sorting expression in the format {@code property,direction}
     * @return a page of hotels matching the supplied criteria
     */
    @Operation(
            summary = "Get hotels",
            description = """
                Returns a paginated list of hotels with filtering support.
                
                The following filters are supported:
                - hotel name;
                - title;
                - city;
                - address;
                - distance to the city center;
                - rating;
                - number of ratings.
                
                The response contains the hotels on the current page
                and the total number of matching hotels.
                """
    )
    @GetMapping
    public HotelPageResponseDTO getHotels(
            @Parameter(description = "Hotel name")
            @RequestParam(required = false) String name,

            @Parameter(description = "Hotel title")
            @RequestParam(required = false) String title,

            @Parameter(description = "City")
            @RequestParam(required = false) String city,

            @Parameter(description = "Hotel address")
            @RequestParam(required = false) String address,

            @Parameter(description = "Distance to the city center")
            @RequestParam(required = false) BigDecimal distanceToCenter,

            @Parameter(description = "Hotel rating")
            @RequestParam(required = false) BigDecimal rating,

            @Parameter(description = "Number of ratings")
            @RequestParam(required = false) Integer numOfRating,

            @Parameter(
                    description = "Page number. Numbering starts from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of hotels per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Sorting in the format: field,direction. For example: name,asc or rating,desc",
                    example = "name,asc"
            )
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        HotelFilterDTO filter = new HotelFilterDTO(
                name,
                title,
                city,
                address,
                distanceToCenter,
                rating,
                numOfRating
        );

        String[] sortParts = sort.split(",", 2);

        Sort.Direction direction = sortParts.length > 1
                ? Sort.Direction.fromString(sortParts[1])
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortParts[0])
        );

        return hotelService.findHotels(filter, pageable);
    }
}
