package com.project.trendora.service;

import com.project.trendora.dto.OrderRequest;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import  java.util.List;
public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);
    List<ViewOrderResponse> viewOrders(Long userId);
    String cancelOrder(Long userId);

}
