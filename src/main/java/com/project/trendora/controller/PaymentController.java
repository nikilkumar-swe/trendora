package com.project.trendora.controller;

import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.dto.PaymentVerificationRequest;
import com.project.trendora.exceptions.PaymentVerificationFailedException;
import com.project.trendora.service.Impl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Slf4j
@Tag(
        name = "Payment Management",
        description = "APIs for creating Razorpay payment orders and verifying payment signatures"
)
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }


    @Operation(
            summary = "Create Razorpay payment",
            description = "Creates a Razorpay payment order for an existing customer order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Payment creation failed")
    })
    @PostMapping("/create/{orderId}")
    public ResponseEntity<PaymentResponseDTO> createPayment( @Parameter(
            description = "Unique ID of the order", example = "1", required = true)
                                                                 @PathVariable Long orderId) throws Exception {

        log.info("payment create request received orderId={}",orderId);
        com.project.trendora.dto.PaymentResponseDTO response =paymentService.createPayment(orderId);
        log.info("Payment created successfully orderId={}",orderId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Verify Razorpay payment",
            description = "Verifies the Razorpay payment signature to validate payment authenticity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid verification request"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Payment verification failed")
    })
    //verify payment
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@Valid @RequestBody PaymentVerificationRequest request) {

        log.info("verify payment request received");

        boolean verified = paymentService.verifyPayment(request);

        if(!verified) {
                log.warn("Payment verification failed | Invalid signature");
                throw new PaymentVerificationFailedException(
                        "Payment verification failed: Invalid payment signature"
                );
            }
            log.info("Payment verified successfully");
            return ResponseEntity.ok("Payment verified successfully");
    }
}
