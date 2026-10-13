package com.project.trendora.controller;

import com.project.trendora.dto.AddToCartRequest;
import com.project.trendora.dto.RemoveFromCartRequest;
import com.project.trendora.dto.ViewCartResponse;
import com.project.trendora.service.Impl.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartServiceImpl cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest){
        log.info("Add to cart request received");
        String response=cartService.addToCart(addToCartRequest);
        log.info("Product added to cart successfully");
        return ResponseEntity.ok(response);
    }
    //remove from cart
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromCart(@Valid @RequestBody RemoveFromCartRequest request){
        log.info("Remove to cart request received ");
        String response=cartService.removeFromCart(request);
        log.info("Product removed successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/view/{userId}")
    public ResponseEntity<ViewCartResponse> viewCart(@PathVariable Long userId){
        log.info("View cart request received userId={}", userId);
        ViewCartResponse response=cartService.viewCart(userId);
        log.info("Cart retrieved successfully userId={}", userId);
        return ResponseEntity.ok(response);
    }

}
