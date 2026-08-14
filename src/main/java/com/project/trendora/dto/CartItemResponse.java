package com.project.trendora.dto;

import lombok.Data;

@Data
public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double subtotal;

}
