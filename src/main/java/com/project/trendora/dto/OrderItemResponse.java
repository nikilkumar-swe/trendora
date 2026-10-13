package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "OrderItemResponse",
        description = "Response DTO containing product details, quantity, price and subtotal for an ordered item"
)
public class OrderItemResponse {
    @Schema(
            description = "Unique ID of the ordered product",
            example = "5"
    )
        private Long productId;
    @Schema(
            description = "Name of the ordered product",
            example = "Nike Air Max Shoes"
    )
        private String productName;
    @Schema(
            description = "Quantity of the product ordered",
            example = "2"
    )
        private Integer quantity;
    @Schema(
            description = "Price per unit of the product",
            example = "2499.00"
    )
        private Double price;
    @Schema(
            description = "Total price for this order item (price × quantity)",
            example = "4998.00"
    )
        private Double subtotal;
}
