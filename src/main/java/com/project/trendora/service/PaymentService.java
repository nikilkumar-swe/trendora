package com.project.trendora.service;

import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.dto.PaymentVerificationRequest;

public interface PaymentService {
    PaymentResponseDTO createPayment(Long orderId);
    boolean verifyPayment(PaymentVerificationRequest request);
}
