package com.project.trendora.service;

import com.project.trendora.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPayment(Long orderId);
}
