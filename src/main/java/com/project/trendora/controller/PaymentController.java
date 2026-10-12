package com.project.trendora.controller;


import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.dto.PaymentVerificationRequest;
import com.project.trendora.service.Impl.PaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //verify payment
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @Valid @RequestBody PaymentVerificationRequest request) {

        boolean verified = paymentService.verifyPayment(request);

        if (verified) {
            return ResponseEntity.ok("Payment verified successfully");
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid payment signature");
    }

}
