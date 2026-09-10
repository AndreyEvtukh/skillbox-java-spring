package com.diploma.skillboxjavaspring.controllers;

import com.diploma.skillboxjavaspring.dto.room.*;
import com.diploma.skillboxjavaspring.services.RoomService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes room-management endpoints.
 *
 * <p>All endpoints are available below {@code /api/v1/room}.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    /**
     * Service that implements room-management operations.
     */
    private final RoomService roomService;

    //-------------------//
    // === GET BY ID === //
    //-------------------//

    /**
     * Retrieves a room by its unique identifier.
     *
     * @param ID the unique identifier of the requested room
     * @return {@code 200 OK} containing the room, or an error response when it is absent
     */
    @Operation(
            summary = "Retrieves a room by ID",
            description = "Returns the room details for the supplied unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<RoomResponseDTO> getById(
            @Parameter(
                    description = "Unique room identifier",
                    required = true,
                    example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
            @PathVariable("id") UUID ID
    ) {
        return ResponseEntity.ok(roomService.getById(ID));
    }

    //--------------//
    // === POST === //
    //--------------//

    /**
     * Creates a room and associates it with the hotel specified in the request.
     *
     * @param roomRequestDTO validated data for the room to create
     * @return {@code 201 Created} containing the created room and its location URI
     */
    @Operation(
            summary = "Add new room",
            description = "Creates a new room and returns the created room."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Room created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "number": "12",
                                        "price": "1280",
                                        "maxCapacity": "4",
                                        "description": "Comfortable room",
                                        "hotelId": "27117d7b-0a5a-4623-8cf0-eaf9091ad969"
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
    public ResponseEntity<RoomResponseDTO> create(
            @Valid @RequestBody RoomRequestDTO roomRequestDTO
    ) {
        log.debug("=> create room {}", roomRequestDTO);
        RoomResponseDTO saved = roomService.create(roomRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    //-------------------//
    // === PUT BY ID === //
    //-------------------//

    /**
     * Updates the details of an existing room.
     *
     * @param ID            the unique identifier of the room to update
     * @param roomUpdateDTO validated replacement data for the room
     * @return {@code 200 OK} containing the updated room, or an error response when absent
     */
    @Operation(
            summary = "Update room",
            description = "Updates an existing room and returns the updated room."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect input data"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PutMapping(
            value = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<RoomResponseDTO> update(
            @Parameter(description = "Unique room identifier", required = true, example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
            @PathVariable("id") UUID ID,
            @Valid @RequestBody RoomUpdateDTO roomUpdateDTO
    ) {
        RoomResponseDTO updated = roomService.update(ID, roomUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    //----------------//
    // === DELETE === //
    //----------------//

    /**
     * Deletes a room by its unique identifier.
     *
     * @param ID the unique identifier of the room to delete
     * @return {@code 204 No Content} when the room is deleted, or an error response when absent
     */
    @Operation(
            summary = "Delete room",
            description = "Deletes the room with the supplied unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") UUID ID
    ) {
        roomService.deleteById(ID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a paginated and sorted collection of rooms matching the supplied filters.
     *
     * @param name             optional room name filter
     * @param number           optional room number filter
     * @param minPrice         optional room minimal price filter
     * @param maxPrice         optional room maximal price filter
     * @param maxCapacity      optional maximum room capacity filter
     * @param hotelIds         optional hotel IDs filter
     * @param bookingDates     optional booking dates filter
     * @param page             zero-based page number
     * @param size             number of rooms per page
     * @param sort             sorting expression in the format {@code property,direction}
     * @return a page of rooms matching the supplied criteria
     */
    @Operation(
            summary = "Get rooms",
            description = """
                Returns a paginated list of rooms with filtering support.
                
                The following filters are supported:
                - room name;
                - room number;
                - room price;
                - maximum room capacity;
                - hotel IDs;
                - booking dates;
                
                The response contains the rooms on the current page
                and the total number of matching rooms.
                """
    )
    @GetMapping
    public RoomPageResponseDTO getRooms(
            @Parameter(description = "Room name")
            @RequestParam(required = false) String name,

            @Parameter(description = "Room number")
            @RequestParam(required = false) Integer number,

            @Parameter(description = "Room Minimal Price")
            @RequestParam(required = false) BigDecimal minPrice,

            @Parameter(description = "Room Maximal Price")
            @RequestParam(required = false) BigDecimal maxPrice,

            @Parameter(description = "Maximum room capacity")
            @RequestParam(required = false) Integer maxCapacity,

            @Parameter(description = "Hotel ID")
            @RequestParam(required = false) List<UUID> hotelIds,

            @Parameter(description = "Booking dates")
            @RequestParam(required = false) List<LocalDate> bookingDates,

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
                    example = "hotelName,asc"
            )
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        RoomFilterDTO filter = new RoomFilterDTO(
                name,
                number,
                minPrice,
                maxPrice,
                maxCapacity,
                hotelIds,
                bookingDates
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

        return roomService.findRooms(filter, pageable);
    }
}
