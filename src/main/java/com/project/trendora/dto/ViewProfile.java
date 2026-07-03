package com.project.trendora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewProfile {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
