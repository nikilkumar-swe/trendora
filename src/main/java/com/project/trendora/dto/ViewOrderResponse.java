package com.project.trendora.dto;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(
        name = "ViewOrderResponse",
        description = "Response DTO containing complete order details, products, payment information, shipping details and order status"
)
public class ViewOrderResponse{
    @Schema(
            description = "Unique ID of the customer order",
            example = "1"
    )
    private Long orderId;
    @Schema(
            description = "List of products included in the order"
    )
    private List<OrderItemResponse> orderItems;
    @Schema(
            description = "Total amount of the order",
            example = "4998.00"
    )
    private Double totalAmount;
    @Schema(
            description = "Payment method selected by the customer"
    )
    private PaymentMethod paymentMethod;
    @Schema(
            description = "Current payment status of the order"
    )
    private PaymentStatus paymentStatus;
    @Schema(
            description = "Shipping address where the order will be delivered",
            example = "House No. 25, Sector 62, Noida, Uttar Pradesh, 201309"
    )
    private String shippingAddress;
    @Schema(
            description = "Date and time when the order was placed",
            example = "2026-10-13T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime orderDate;
    @Schema(
            description = "Date and time when the order was delivered; null if not yet delivered",
            example = "2026-10-16T15:45:00",
            type = "string",
            format = "date-time",
            nullable = true
    )
    private LocalDateTime deliveryDate;

    @Schema(
            description = "Current lifecycle status of the order"
    )
    private OrderStatus orderStatus;
}