package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "AddToCartRequest",
        description = "Request DTO for adding a product to the user's shopping cart"
)
public class AddToCartRequest {
    @Schema(
            description = "Unique ID of the user",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message ="userId cannot be empty")
    private Long userId;

    @Schema(
            description = "Unique ID of the product to add to cart",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "productId cannot be empty")
    private Long productId;
    @Schema(
            description = "Quantity of the product to add to cart",
            example = "2",
            defaultValue = "1",
            minimum = "1"
    )
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
