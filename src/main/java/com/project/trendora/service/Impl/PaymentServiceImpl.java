package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.Repository.PaymentRepository;
import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import com.project.trendora.models.Order;
import com.project.trendora.models.Payment;
import com.project.trendora.service.PaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
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

    public PaymentServiceImpl(
            RazorpayClient razorpayClient,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.razorpayClient = razorpayClient;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

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

}