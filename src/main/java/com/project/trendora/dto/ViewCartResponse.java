package com.project.trendora.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ViewCartResponse {
    @NotNull(message = "cartId cannot be null")
    private Long cartId;
    @NotNull(message ="userId cannot be null")
    private Long userId;

    private List<CartItemResponse> items;
    private Integer totalItems;
    private Double totalAmount;

}
