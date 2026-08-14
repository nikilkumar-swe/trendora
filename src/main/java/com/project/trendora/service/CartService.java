package com.project.trendora.service;

import com.project.trendora.dto.AddToCartRequest;
import com.project.trendora.dto.RemoveFromCartRequest;
import com.project.trendora.dto.ViewCartResponse;

public interface CartService {

     String addToCart(AddToCartRequest addToCartRequest);
     String removeFromCart(RemoveFromCartRequest request);
     ViewCartResponse viewCart(Long userId);
}
