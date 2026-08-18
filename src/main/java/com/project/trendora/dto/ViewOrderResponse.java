package com.project.trendora.dto;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ViewOrderResponse{
    private Long orderId;
    private List<OrderItemResponse> orderItems;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private OrderStatus orderStatus;
}