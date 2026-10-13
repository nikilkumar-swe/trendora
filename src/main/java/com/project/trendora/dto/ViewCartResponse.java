package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "ViewCartResponse",
        description = "Response DTO containing user cart details, cart items, total quantity and total amount"
)
public class ViewCartResponse {

    @Schema(
            description = "Unique ID of the shopping cart",
            example = "1"
    )
    private Long cartId;

    @Schema(
            description = "Unique ID of the cart owner",
            example = "10"
    )
    private Long userId;

    @Schema(
            description = "List of products available in the shopping cart"
    )
    private List<CartItemResponse> items;

    @Schema(
            description = "Total quantity of all products in the cart",
            example = "3",
            minimum = "0"
    )
    private Integer totalItems;

    @Schema(
            description = "Total amount of all products in the cart",
            example = "7497.00",
            minimum = "0"
    )
    private Double totalAmount;
}
