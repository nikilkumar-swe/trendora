package com.project.trendora.service;


import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import com.project.trendora.models.Order;

public interface AdminService {
    String acceptOrder(Long userId);
    OrderResponse processOrder(Long orderId);
    OrderResponse shipOrder(Long orderId);
    OrderResponse orderDelivery(Long orderId);
}
