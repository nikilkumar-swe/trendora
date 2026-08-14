package com.project.trendora.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotNull(message ="userId cannot be empty")
    private Long userId;
    @NotNull(message = "productId cannot be empty")
    private Long productId;
    private Integer quantity;
}
