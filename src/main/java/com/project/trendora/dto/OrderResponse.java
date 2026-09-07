package com.project.trendora.dto;

import com.project.trendora.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemResponse> orderItems;
}
