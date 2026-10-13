package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(
        name = "RemoveFromCartRequest",
        description = "Request DTO for removing a product or reducing its quantity in the user's shopping cart"
)
public class RemoveFromCartRequest {
    @Schema(
            description = "Unique ID of the user",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "userId cannot be empty")
    private Long userId;

    @Schema(
            description = "Unique ID of the product to remove from the cart",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "productId cannot be empty")
    private Long productId;

    @Schema(
            description = "Quantity of the product to remove from the cart",
            example = "2",
            minimum = "1"
    )
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;
}
