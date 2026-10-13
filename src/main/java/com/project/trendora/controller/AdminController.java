package com.project.trendora.controller;

import com.project.trendora.dto.OrderResponse;
import com.project.trendora.models.Order;
import com.project.trendora.service.Impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminServices;

    public AdminController(AdminServiceImpl adminServices){
        this.adminServices=adminServices;
    }

    @PutMapping("/orders/accept/{orderId}")
    public ResponseEntity<String> acceptOrder(@PathVariable Long orderId){
        log.info("Admin requested order acceptance orderId={}", orderId);
        String response=adminServices.acceptOrder(orderId);
        log.info("Order accepted successfully orderId={}", orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{orderId}/process")
    public ResponseEntity<OrderResponse> OrderProcessing(@PathVariable Long orderId){
        log.info("Admin requested order processing orderId={}", orderId);
        OrderResponse order=adminServices.processOrder(orderId);
        log.info("Order processed successfully orderId={}", orderId);
        return ResponseEntity.ok(order);
    }
    @PutMapping("/orders/{orderId}/ship")
    public  ResponseEntity<OrderResponse> orderShipping(@PathVariable Long orderId){
        log.info("Admin requested order shipping orderId={}",orderId);
        OrderResponse order=adminServices.shipOrder(orderId);
        log.info("Order shipped successfully orderId={}",orderId);
        return ResponseEntity.ok().body(order);
    }
    @PutMapping("/orders/{orderId}/deliver")
    public ResponseEntity<OrderResponse> isDelivered(@PathVariable Long orderId){
        log.info("Admin requested order delivering orderId={}",orderId);
        OrderResponse order=adminServices.orderDelivery(orderId);
        log.info("Order delivered successfully orderId={}",orderId);
        return ResponseEntity.ok().body(order);

    }



}
