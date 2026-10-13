package com.project.trendora.dto;

import com.project.trendora.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "OrderRequest",
        description = "Request DTO for placing a new customer order"
)
public class OrderRequest {
    @Schema(
            description = "Unique ID of the customer placing the order",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "userId field cannot be empty")
    private Long userId;
    @Schema(
            description = "Payment method selected by the customer",
            example = "ONLINE"
    )
    private PaymentMethod paymentMethod;
    @Schema(
            description = "Shipping address where the order will be delivered",
            example = "House No. 25, Sector 62, Noida, Uttar Pradesh, 201309"
    )
    private String shippingAddress;

}
