package com.project.trendora.controller;

import com.project.trendora.dto.OrderRequest;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import com.project.trendora.service.Impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/place/{userId}")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
        log.info("place order request received");
        OrderResponse response=orderService.placeOrder(orderRequest);
        log.info("Order placed successfully");
        return ResponseEntity.ok(response);
    }
    //get all orders of a user
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<ViewOrderResponse>> viewOrder(@PathVariable Long userId){
        log.info("view order request received userId={}",userId);
        List<ViewOrderResponse> orders=orderService.viewOrders(userId);
        log.info("Order retrieved successfully userId={} count={}",userId,orders.size());
        return ResponseEntity.ok(orders);

    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
       log.info("Cancel order request received orderId={}",orderId);
        java.lang.String response =orderService.cancelOrder(orderId);
        log.info("Order cancelled successfully orderId={}",orderId);
        return ResponseEntity.ok(response);
    }

}
