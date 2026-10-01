package com.project.trendora.Repository;

import com.project.trendora.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    Optional<Payment> findByOrder_OrderId(Long orderId);
}
