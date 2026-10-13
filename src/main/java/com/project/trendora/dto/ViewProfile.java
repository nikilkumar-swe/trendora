package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ViewProfile",
        description = "Response DTO containing user profile information"
)
public class ViewProfile {

    @Schema(
            description = "First name of the user",
            example = "Nikhil"
    )
    private String firstName;
    @Schema(
            description = "Last name of the user",
            example = "Kumar"
    )
    private String lastName;
    @Schema(
            description = "Unique username of the user",
            example = "nikhil123"
    )
    private String username;
    @Schema(
            description = "Registered email address of the user",
            example = "nikhil@example.com",
            format = "email"
    )
    private String email;
}
