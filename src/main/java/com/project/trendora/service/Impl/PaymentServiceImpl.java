package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.Repository.PaymentRepository;
import com.project.trendora.dto.PaymentResponseDTO;
import com.project.trendora.dto.PaymentVerificationRequest;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import com.project.trendora.exceptions.OrderNotFoundException;
import com.project.trendora.exceptions.PaymentVerificationFailedException;
import com.project.trendora.models.Order;
import com.project.trendora.models.Payment;
import com.project.trendora.service.PaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
        log.info("Payment creation started | orderId={}", orderId);
        // find trendora order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

            // Get amount from backend
            Double amount = order.getTotalAmount();

            // Razorpay expects amount in paise
           int amountInPaise = (int) Math.round(amount * 100);

           try {
            // Create Razorpay order
            JSONObject options = new JSONObject();

            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "order_" + orderId);

            log.info("Creating Razorpay order | orderId={}", orderId);

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(options);

            String razorpayOrderId = razorpayOrder.get("id");

            // Save payment in database
            Payment payment = new Payment();

            payment.setOrder(order);
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setAmount(amount);
            payment.setStatus(PaymentStatus.CREATED);
            payment.setMethod(PaymentMethod.RAZORPAY);

            paymentRepository.save(payment);

               log.info("Payment record created | orderId={} | razorpayOrderId={}",
                       orderId, razorpayOrderId);

               //Return data to frontend
            PaymentResponseDTO response = new PaymentResponseDTO();

            response.setOrderId(orderId);
            response.setRazorpayOrderId(razorpayOrderId);
            response.setAmount(amount);
            response.setCurrency("INR");
            return response;

        }catch(RazorpayException e){
               log.error("Razorpay order creation failed | orderId={}", orderId, e);
               throw new IllegalStateException("Failed to create Razorpay order", e);
        }

    }

    //verify payment
    @Override
    @Transactional
    public boolean verifyPayment(PaymentVerificationRequest request) {
        log.info("Payment verification started");

        // Find the payment we created earlier
        Payment payment = paymentRepository
                .findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new PaymentVerificationFailedException(
                        "payment record not found"
                ));

        Order order = payment.getOrder();

        try {
            // Prepare Razorpay verification data
            JSONObject options = new JSONObject();

            // IMPORTANT: Use order id stored in DB
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

            // Verify signature
            boolean valid = Utils.verifyPaymentSignature(
                    options,
                    razorpayKeySecret
            );

            if (!valid) {
                log.warn("Invalid payment signature | orderId={}", payment.getOrder().getOrderId());
                throw new PaymentVerificationFailedException("Invalid payment signature");
            }

            // Fetch payment details from Razorpay
            com.razorpay.Payment razorpayPayment =
                    razorpayClient.payments.fetch(
                            request.getRazorpayPaymentId()
                    );

            String razorpayStatus = razorpayPayment.get("status");
            String razorpayOrderId = razorpayPayment.get("order_id");
            String currency = razorpayPayment.get("currency");
            Number paidAmount = razorpayPayment.get("amount");

            long expectedAmount = java.math.BigDecimal
                    .valueOf(order.getTotalAmount())
                    .movePointRight(2)
                    .longValueExact();

            // Validate actual payment
            if (!"captured".equals(razorpayStatus)
                    || !payment.getRazorpayOrderId()
                    .equals(razorpayOrderId)
                    || !"INR".equals(currency)
                    || paidAmount.longValue() != expectedAmount) {

                log.warn("Payment details mismatch | orderId={}",
                        order.getOrderId());

                throw new PaymentVerificationFailedException(
                        "Payment not captured or details mismatch"
                );
            }

            // Verification successful : Update payments table
            payment.setRazorpayPaymentId(request.getRazorpayPaymentId() );
            payment.setRazorpaySignature(request.getRazorpaySignature());
            // Adapt enum/value to your existing Payment entity
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            order.setPaymentStatus(PaymentStatus.SUCCESS);
            orderRepository.saveAndFlush(order);
            log.info("Order payment status updated | orderId={} | status={}",
                    order.getOrderId(),
                    order.getPaymentStatus());

            log.info("Payment signature verified | orderId={}", payment.getOrder().getOrderId());
            return true;

        } catch (RazorpayException e) {
            log.error("Razorpay verification error | orderId={}", payment.getOrder().getOrderId(), e);
            throw new PaymentVerificationFailedException("Payment verification failed");
        }

    }
}