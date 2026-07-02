package com.project.trendora.dto;

import com.project.trendora.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "userId field cannot be empty")
    private Long userId;
    private PaymentMethod paymentMethod;
    private String shippingAddress;

}
