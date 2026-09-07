package com.diploma.skillboxjavaspring.controlers;

import com.diploma.skillboxjavaspring.dto.user.UserRequestDTO;
import com.diploma.skillboxjavaspring.dto.user.UserResponseDTO;
import com.diploma.skillboxjavaspring.dto.user.UserUpdateDTO;
import com.diploma.skillboxjavaspring.exceptions.UserEmailExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserIDNotFoundException;
import com.diploma.skillboxjavaspring.exceptions.UserNameExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserNameNotFoundException;
import com.diploma.skillboxjavaspring.services.UserService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Exposes REST endpoints for managing users.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * Service that implements user-management operations.
     */
    private final UserService userService;

    //---------------------//
    // ===== GET ALL ===== //
    //---------------------//

    /**
     * Returns all users.
     *
     * @return an HTTP 200 response containing the list of users
     */
    @Operation(
            summary = "Get all user",
            description = "Returns the list of all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    //---------------------//
    // === GET BY NAME === //
    //---------------------//

    /**
     * Returns a user identified by username.
     *
     * @param username the username to search for
     * @return an HTTP 200 response containing the user data
     * @throws UserNameNotFoundException if no user has the specified username
     */
    @Operation(
            summary = "Find user",
            description = "Returns the user details for the supplied user name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(params = "username")
    public ResponseEntity<UserResponseDTO> getByUsername(
            @Parameter(
                    description = "User name",
                    required = true,
                    example = "Bill Duke")
            @RequestParam String username
    ) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    //--------------//
    // === POST === //
    //--------------//

    /**
     * Creates a user from the supplied request data.
     *
     * @param userRequestDTO the validated data for the user to create
     * @return an HTTP 201 response containing the created user and its location
     * @throws UserNameExistedException  if the username is already in use
     * @throws UserEmailExistedException if the email address is already in use
     */
    @Operation(
            summary = "Add new user",
            description = "Creates a new user and returns the created user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "email": "example@mail.com",
                                        "username": "Bill Duke",
                                        "role": "USER"
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
    public ResponseEntity<UserResponseDTO> create(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        log.debug("=> Received request to create new user {}", userRequestDTO);
        UserResponseDTO saved = userService.create(userRequestDTO);

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
     * Updates a user identified by its unique ID.
     *
     * @param id            the unique ID of the user to update
     * @param userUpdateDTO the validated updated user data
     * @return an HTTP 200 response containing the updated user
     * @throws UserIDNotFoundException   if no user has the specified ID
     * @throws UserNameExistedException  if the username is already in use
     * @throws UserEmailExistedException if the email address is already in use
     */
    @Operation(
            summary = "Update user",
            description = "Updates an existing user and returns the updated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(
            consumes = "application/json",
            produces = "application/json",
            value = "/{id}"
    )
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        UserResponseDTO updated = userService.update(id, userUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    //----------------//
    // === DELETE === //
    //----------------//

    /**
     * Deletes a user identified by its unique ID.
     *
     * @param ID the unique ID of the user to delete
     * @return an HTTP 204 response when the user is deleted
     * @throws UserIDNotFoundException if no user has the specified ID
     */
    @Operation(
            summary = "Delete user",
            description = "Deletes the user with the supplied unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") UUID ID
    ) {
        userService.delete(ID);
        return ResponseEntity.noContent().build();
    }
}
