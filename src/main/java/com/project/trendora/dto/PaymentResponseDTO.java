package com.project.trendora.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String razorpayOrderId;
    private Long orderId;
    private Double amount;
    private String currency;

}
