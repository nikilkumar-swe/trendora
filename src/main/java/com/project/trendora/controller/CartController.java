package com.project.trendora.controller;

import com.project.trendora.dto.AddToCartRequest;
import com.project.trendora.dto.RemoveFromCartRequest;
import com.project.trendora.dto.ViewCartResponse;
import com.project.trendora.service.Impl.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest){
        return ResponseEntity.ok(cartService.addToCart(addToCartRequest));
    }
    //remove from cart
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromCart(@Valid @RequestBody RemoveFromCartRequest request){
        return ResponseEntity.ok(cartService.removeFromCart(request));
    }
    @GetMapping("/view/{userId}")
    public ResponseEntity<ViewCartResponse> viewCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.viewCart(userId));
    }

}
