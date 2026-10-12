package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.Repository.PaymentRepository;
import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.dto.PaymentVerificationRequest;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import com.project.trendora.models.Order;
import com.project.trendora.models.Payment;
import com.project.trendora.service.PaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpayClient;

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public PaymentServiceImpl(
            RazorpayClient razorpayClient,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.razorpayClient = razorpayClient;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    //create payment
    @Override
    public PaymentResponseDTO createPayment(Long orderId) {
        try {
            // 1. Find Trendora order
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new RuntimeException("Order not found"));

            // 2. Get amount from backend
            Double amount = order.getTotalAmount();

            // Razorpay expects amount in paise
            int amountInPaise = (int) (amount * 100);

            // 3. Create Razorpay order
            JSONObject options = new JSONObject();

            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "order_" + orderId);

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(options);

            String razorpayOrderId = razorpayOrder.get("id");

            // 4. Save payment in database
            Payment payment = new Payment();

            payment.setOrder(order);
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setAmount(amount);
            payment.setStatus(PaymentStatus.CREATED);
            payment.setMethod(PaymentMethod.RAZORPAY);

            paymentRepository.save(payment);

            // 5. Return data to frontend
            PaymentResponseDTO response = new PaymentResponseDTO();

            response.setOrderId(orderId);
            response.setRazorpayOrderId(razorpayOrderId);
            response.setAmount(amount);
            response.setCurrency("INR");
            return response;

        }catch(RazorpayException e){
            throw new RuntimeException("failed to create Razorpay order",e);
        }

    }


    //verify payment
    @Override
    @Transactional
    public boolean verifyPayment(PaymentVerificationRequest request) {

        // 1. Find the payment we created earlier
        Payment payment = paymentRepository
                .findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {

            // 2. Prepare Razorpay verification data
            JSONObject options = new JSONObject();

            // IMPORTANT:
            // Use order id stored in DB
            options.put(
                    "razorpay_order_id",
                    payment.getRazorpayOrderId()
            );

            options.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId()
            );

            options.put(
                    "razorpay_signature",
                    request.getRazorpaySignature()
            );

            // 3. Verify signature
            boolean valid = Utils.verifyPaymentSignature(
                    options,
                    razorpayKeySecret
            );

            if (!valid) {
                return false;
            }

            // 4. Verification successful
            payment.setRazorpayPaymentId(
                    request.getRazorpayPaymentId()
            );

            payment.setRazorpaySignature(
                    request.getRazorpaySignature()
            );

            // Adapt enum/value to your existing Payment entity
            payment.setStatus(PaymentStatus.SUCCESS);

            paymentRepository.save(payment);

            return true;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Payment verification failed",
                    e
            );
        }

    }
}