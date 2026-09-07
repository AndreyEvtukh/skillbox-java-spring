package com.diploma.skillboxjavaspring.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Carries the validated data used to update an existing user.
 */
@Schema(description = "Data for updating a user")
public record UserUpdateDTO() {

    /**
     * The user's updated display name.
     */
    @NotBlank(message = "Name can not be blank")
    @Pattern(
            regexp = "^\\S(?:.*\\S)?$",
            message = "Username must not start or end with a space"
    )
    @Schema(description = "User name", example = "Bill Duke")
    private static String username;

    /**
     * The user's updated email address.
     */
    @NotBlank(message = "Email can not be blank")
    @Schema(description = "User email", example = "example@mail.com")
    private static String email;

    /**
     * The user's updated password.
     */
    @NotBlank(message = "Password can not be blank")
    @Schema(description = "User password", example = "1245")
    private static String password;
}
