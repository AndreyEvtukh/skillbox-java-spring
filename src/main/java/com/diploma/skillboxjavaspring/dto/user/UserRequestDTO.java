package com.diploma.skillboxjavaspring.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Carries the validated data required to create a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data for creating a user")
public class UserRequestDTO {

    /**
     * The user's display name.
     */
    @NotBlank(message = "Name can not be blank")
    @Pattern(
            regexp = "^\\S(?:.*\\S)?$",
            message = "Username must not start or end with a space"
    )
    @Schema(description = "User name", example = "Bill Duke")
    private String username;

    /**
     * The user's email address.
     */
    @NotBlank(message = "Email can not be blank")
    @Schema(description = "User email", example = "example@mail.com")
    private String email;

    /**
     * The encoded password for the user.
     */
    @NotBlank(message = "Password can not be blank")
    @Schema(description = "User password hash", example = "Password")
    private String password;

}
