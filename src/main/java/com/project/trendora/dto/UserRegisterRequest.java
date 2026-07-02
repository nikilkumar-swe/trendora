package com.project.trendora.dto;

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
public class UserRegisterRequest {
    @NotBlank(message = "Firstname name can not be blank")
    @Size(min = 3,max = 20)
    private String firstName;
    @NotBlank(message = "Lastname field cannot be empty")
    private String lastName;
    @Size(min = 3,max = 30)
    private String username;
    @Email(message = "Email is required")
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain one uppercase letter and one digit")
    private String password;

}
