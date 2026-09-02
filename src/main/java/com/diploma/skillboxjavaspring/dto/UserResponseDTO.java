package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.UUID;

/**
 * Represents user data returned by the API.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User response")
public class UserResponseDTO {

    /**
     * The user's unique identifier.
     */
    @Schema(description = "Unique user identifier", example = "27117d7b-0a5a-4623-8cf0-eaf9091ad969")
    private UUID id;

    /**
     * The user's unique email address.
     */
    @Schema(description = "Unique user email", example = "example@mail.com")
    private String email;

    /**
     * The user's display name.
     */
    @Schema(description = "User name", example = "Bill Duke")
    private String username;

    /**
     * The user's assigned role.
     */
    @Schema(description = "User role", example = "ADMIN")
    private String role;

}
