package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "UserRegisterRequest",
        description = "Request DTO for registering a new user in Trendora"
)
public class UserRegisterRequest {

    @NotBlank(message = "Firstname name can not be blank")
    @Size(min = 3,max = 20)
    @Schema(
            description = "First name of the user",
            example = "Nikhil",
            minLength = 3,
            maxLength = 20,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @NotBlank(message = "Lastname field cannot be empty")
    @Schema(
            description = "Last name of the user",
            example = "Kumar",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName;

    @Size(min = 3,max = 30)
    @Schema(
            description = "Unique username for the user account",
            example = "nikhil123",
            minLength = 3,
            maxLength = 30,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Email(message = "Email is required")
    @Schema(
            description = "Valid email address of the user",
            example = "nikhil@example.com",
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain one uppercase letter and one digit")
    @Schema(
            description = "Account password containing at least 8 characters, one uppercase letter and one digit",
            example = "Password123",
            format = "password",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            minLength = 8,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

}
