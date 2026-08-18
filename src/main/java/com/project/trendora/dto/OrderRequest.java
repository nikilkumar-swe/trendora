package com.project.trendora.dto;

import com.project.trendora.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
    @NotNull(message = "userId field cannot be empty")
    private Long userId;
    private PaymentMethod paymentMethod;
    private String shippingAddress;

}
