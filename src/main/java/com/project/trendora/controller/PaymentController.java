package com.project.trendora.controller;


import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.service.Impl.PaymentServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create/{orderId}")
    public PaymentResponseDTO createPayment(
            @PathVariable Long orderId)
            throws Exception {

        return paymentService.createPayment(orderId);
    }

}
