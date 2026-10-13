package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "UserRegisterResponse",
        description = "Response DTO containing registered user details and account creation timestamps"
)
public class UserRegisterResponse {
    @Schema(
            description = "First name of the registered user",
            example = "Nikhil"
    )
    private String firstName;
    @Schema(
            description = "Last name of the registered user",
            example = "Kumar"
    )
    private String lastName;

    @Schema(
            description = "Unique username of the registered user",
            example = "nikhil123"
    )
    private String username;

    @Schema(
            description = "Email address of the registered user",
            example = "nikhil@example.com",
            format = "email"
    )
    private String email;

    @Schema(
            description = "Date and time when the user account was created",
            example = "2026-10-13T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;
    @Schema(
            description = "Date and time when the user account was last updated",
            example = "2026-10-13T12:45:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime updatedAt;
}
