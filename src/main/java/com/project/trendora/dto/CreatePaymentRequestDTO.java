package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CreatePaymentRequestDTO",
        description = "Request DTO for creating a Razorpay payment order"
)
public class CreatePaymentRequestDTO {
    @Schema(
            description = "Unique ID of the customer order for which payment will be created",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long orderId;

}
