package com.project.trendora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotBlank(message ="userId cannot be empty")
    private Long userId;
    @NotBlank(message = "productId cannot be empty")
    private Long productId;
    private Integer quantity;
}
