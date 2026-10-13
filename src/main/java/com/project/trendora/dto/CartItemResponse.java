package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CartItemResponse",
        description = "Response DTO containing product details, quantity and subtotal of a cart item"
)
public class CartItemResponse {

    @Schema(
            description = "Unique ID of the cart item",
            example = "1"
    )
    private Long cartItemId;
    @Schema(
            description = "Unique ID of the product",
            example = "5"
    )
    private Long productId;
    @Schema(
            description = "Name of the product",
            example = "Nike Air Max Shoes"
    )
    private String productName;
    @Schema(
            description = "Price per unit of the product",
            example = "2499.00"
    )
    private Double price;
    @Schema(
            description = "Quantity of the product in the cart",
            example = "2"
    )
    private Integer quantity;
    @Schema(
            description = "Total price for this cart item (price × quantity)",
            example = "4998.00"
    )
    private Double subtotal;

}
