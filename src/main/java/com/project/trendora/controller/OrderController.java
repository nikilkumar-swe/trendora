package com.project.trendora.controller;

import com.project.trendora.dto.OrderRequest;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import com.project.trendora.service.Impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/place/{userId}")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }
    //get all orders of a user
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<ViewOrderResponse>> viewOrder(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.viewOrders(userId));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

}
