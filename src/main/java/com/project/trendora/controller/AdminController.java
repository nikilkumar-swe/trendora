package com.project.trendora.controller;

import com.project.trendora.dto.OrderResponse;
import com.project.trendora.models.Order;
import com.project.trendora.service.Impl.AdminServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminServiceImpl adminServices;
    public AdminController(AdminServiceImpl adminServices){
        this.adminServices=adminServices;
    }

    @PutMapping("/orders/accept/{orderId}")
    public ResponseEntity<String> acceptOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(adminServices.acceptOrder(orderId));
    }

    @PutMapping("/orders/{orderId}/process")
    public ResponseEntity<OrderResponse> OrderProcessing(@PathVariable Long orderId){
        OrderResponse order=adminServices.processOrder(orderId);
        return ResponseEntity.ok(order);
    }
    @PutMapping("/orders/{orderId}/ship")
    public  ResponseEntity<OrderResponse> orderShipping(@PathVariable Long orderId){
        OrderResponse order=adminServices.shipOrder(orderId);
        return ResponseEntity.ok().body(order);
    }
    @PutMapping("/orders/{orderId}/deliver")
    public ResponseEntity<OrderResponse> isDelivered(@PathVariable Long orderId){
        OrderResponse order=adminServices.orderDelivery(orderId);
        return ResponseEntity.ok().body(order);
    }



}
