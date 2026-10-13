package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "LoginRequest",
        description = "Request DTO for authenticating users using username and password"
)
public class LoginRequest {
    @Schema(
            description = "Registered username of the user",
            example = "nikhil123",
            minLength = 3,
            maxLength = 30,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Size(min = 3,max = 30)
    private String username;

    @Schema(
            description = "Password associated with the user account",
            example = "Password123",
            format = "password",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain one uppercase letter and one digit")
    private String password;
}
