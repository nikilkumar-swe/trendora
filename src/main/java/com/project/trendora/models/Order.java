package com.project.trendora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.enums.PaymentMethod;
import com.project.trendora.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    //Which user place the order
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonIgnore
    private User user;
    //Items of Order
    @OneToMany(mappedBy ="order" ,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    //total order amount
    @Column(nullable = false)
    private Double totalAmount;
    //order status
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //payment method
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    //payment status
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    //delivery address
    private String shippingAddress;
    //when order placed
    private LocalDateTime orderDate;
    //expected delivery date
    private LocalDateTime deliveryDate;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PENDING;
        this.deliveryDate=LocalDateTime.now().plusDays(4);
        this.status=OrderStatus.PLACED;
    }

}
