package com.project.trendora.dto;

import com.project.trendora.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "OrderResponse",
        description = "Response DTO containing order details, total amount, order status and ordered products"
)
public class OrderResponse {

    @Schema(
            description = "Unique ID of the order",
            example = "1"
    )
    private Long orderId;

    @Schema(
            description = "Total amount of the order",
            example = "4998.00"
    )
    private Double totalAmount;
    @Schema(
            description = "Current status of the order",
            example = "PENDING"
    )
    private OrderStatus status;
    @Schema(
            description = "List of products included in the order"
    )
    private List<OrderItemResponse> orderItems;
}
