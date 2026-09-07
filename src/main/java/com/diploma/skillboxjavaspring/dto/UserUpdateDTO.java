package com.diploma.skillboxjavaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Carries the validated data used to update an existing user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data for updating a user")
public class UserUpdateDTO {

    /**
     * The user's updated display name.
     */
    @NotBlank(message = "Name can not be blank")
    @Pattern(
            regexp = "^\\S(?:.*\\S)?$",
            message = "Username must not start or end with a space"
    )
    @Schema(description = "User name", example = "Bill Duke")
    private String username;

    /**
     * The user's updated email address.
     */
    @NotBlank(message = "Email can not be blank")
    @Schema(description = "User email", example = "example@mail.com")
    private String email;

    /**
     * The user's updated password.
     */
    @NotBlank(message = "Password can not be blank")
    @Schema(description = "User password", example = "1245")
    private String password;
}
