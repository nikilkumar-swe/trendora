package com.project.trendora.service.Impl;

import com.project.trendora.Repository.CartItemRepository;
import com.project.trendora.Repository.CartRepository;
import com.project.trendora.Repository.ProductRepository;
import com.project.trendora.Repository.UserRepository;
import com.project.trendora.dto.AddToCartRequest;
import com.project.trendora.dto.CartItemResponse;
import com.project.trendora.dto.RemoveFromCartRequest;
import com.project.trendora.dto.ViewCartResponse;
import com.project.trendora.models.Cart;
import com.project.trendora.models.CartItems;
import com.project.trendora.models.Product;
import com.project.trendora.models.User;
import com.project.trendora.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public String addToCart(AddToCartRequest addToCartRequest) {
        log.info("Adding item to cart for user {}", addToCartRequest.getUserId());
        // find the user
        User user = userRepository.findById(addToCartRequest.getUserId())
                .orElseThrow(() -> {
                    log.warn("User not found with id {}",addToCartRequest.getUserId());
                   return new RuntimeException("User not found");
                });

        // find the product
        Product product = productRepository.findById(addToCartRequest.getProductId())
                .orElseThrow(() ->{
                    log.warn("Product not found with id {}", addToCartRequest.getProductId());
                    return new RuntimeException("Product not found");
                });

        // get or create cart
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
            log.info("New cart created for user {}", user.getUserId());
        }

        // check if product already exists in cart
        Optional<CartItems> existingItem =
                cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItems item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + addToCartRequest.getQuantity()
            );

            item.setSubtotal(
                    item.getQuantity() * product.getPrice()
            );

            cartItemRepository.save(item);
            log.info("Updated quantity of product {} in cart of user {}", product.getProductId(),user.getUserId());

        } else {

            CartItems item = new CartItems();

            item.setCart(cart);

            item.setProduct(product);

            item.setQuantity(addToCartRequest.getQuantity());

            item.setSubtotal(
                    addToCartRequest.getQuantity() * product.getPrice()
            );

            cartItemRepository.save(item);
            log.info("Added product {} to cart of user {}", product.getProductId(), user.getUserId());
        }

        log.info("Item added to cart successfully for user {}", user.getUserId());
        return "Item added to cart successfully";

    }


    @Override
    public String removeFromCart(RemoveFromCartRequest request) {
        log.info("Removing product {} from cart for user {}", request.getProductId(), request.getUserId());

        //find user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->{
                    log.warn("User not found with id {}", request.getUserId());
                            return new RuntimeException("User not found");
                });
        //find product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->{
                    log.warn("Product not found with id {}",request.getProductId());
                    return new RuntimeException("Product not found");
                });

        //find the cart of the user
        Cart cart = cartRepository.findByUser(user);
        //if cart is not there
        if (cart == null) {
            log.warn("Cart not found for user {}", user.getUserId());
            throw new RuntimeException("Cart not found");
        }

        //extract added items in cart
        CartItems item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() ->{
                    log.warn("Product {} is not present in cart of user {}",product.getProductId(),user.getUserId());
                    return new RuntimeException("Product not present in cart");
                });

        // quantity kam karni hai
        if (item.getQuantity() > request.getQuantity()) {

            int newQuantity = item.getQuantity() - request.getQuantity();

            item.setQuantity(newQuantity);

            item.setSubtotal(newQuantity * product.getPrice());

            cartItemRepository.save(item);
            log.info("Reduced quantity of product {} to {} for user {}", product.getProductId(), newQuantity, user.getUserId());

        } else {
            // quantity equal ya zyada remove karne par poora item delete
            cartItemRepository.delete(item);
            log.info("Removed product {} completely from cart of user {}", product.getProductId(), user.getUserId());
        }
        log.info("Item removed successfully for user {}", user.getUserId());
        return "Item removed successfully";
    }

    @Override
    public ViewCartResponse viewCart(Long userId) {

        // 1. Check user existence
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Find user's cart
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        // 3. Get cart items
        List<CartItems> cartItems = cart.getCartItems();

        // 4. Create response
        ViewCartResponse response = new ViewCartResponse();

        response.setCartId(cart.getCartId());
        response.setUserId(user.getUserId());

        // 5. Create list for DTOs
        List<CartItemResponse> itemResponses = new ArrayList<>();

        int totalItems = 0;
        double totalAmount = 0;

        // 6. Convert CartItems -> CartItemResponse
        for (CartItems cartItem : cartItems) {

            CartItemResponse itemResponse = new CartItemResponse();

            itemResponse.setCartItemId(cartItem.getId());

            itemResponse.setProductId(
                    cartItem.getProduct().getProductId()
            );

            itemResponse.setProductName(
                    cartItem.getProduct().getName()
            );

            itemResponse.setPrice(
                    cartItem.getProduct().getPrice()
            );

            itemResponse.setQuantity(
                    cartItem.getQuantity()
            );

            itemResponse.setSubtotal(
                    cartItem.getSubtotal()
            );

            itemResponses.add(itemResponse);

            // calculate totals
            totalItems += cartItem.getQuantity();
            totalAmount += cartItem.getSubtotal();
        }

        // 7. Set final values
        response.setItems(itemResponses);
        response.setTotalItems(totalItems);
        response.setTotalAmount(totalAmount);

        return response;
    }

}
