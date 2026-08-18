package com.project.trendora.service.Impl;

import com.project.trendora.Repository.CartItemRepository;
import com.project.trendora.Repository.CartRepository;
import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.Repository.UserRepository;
import com.project.trendora.dto.OrderItemResponse;
import com.project.trendora.dto.OrderRequest;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.exceptions.ProductNotFoundException;
import com.project.trendora.models.*;
import com.project.trendora.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private  final OrderRepository orderRepository;

    //To place order
    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        log.info("Placing order for user {}", orderRequest.getUserId());
        //extract the user
        User user=userRepository.findById(orderRequest.getUserId()).orElseThrow(
                ()->{
                    log.warn("User not found with id {}", orderRequest.getUserId());
                    return new RuntimeException("User not found");
                }
        );
        //extract the cart of user
        Cart cart=cartRepository.findByUser(user);
        if (cart == null || cart.getCartItems().isEmpty()) {
            log.warn("Cart is empty for user {}", user.getUserId());
            throw new RuntimeException("Cart is empty");
        }

        //create an order object
        Order order=new Order();
        order.setUser(user);
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setShippingAddress(orderRequest.getShippingAddress());
        //calculate the total amount
        double totalAmount=0;
        for (CartItems cartItem:cart.getCartItems()){
            totalAmount=totalAmount+cartItem.getProduct().getPrice() *cartItem.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        //create order Items

        List<OrderItem> orderItems=new ArrayList<>();
        for (CartItems cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());

            double price = cartItem.getProduct().getPrice();
            int quantity = cartItem.getQuantity();

            orderItem.setPriceAtPurchase(price);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(price * quantity);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        //save the order
        Order savedOrder = orderRepository.save(order);
        log.info("Order {} placed successfully for user {}",savedOrder.getOrderId(), user.getUserId());

        //clear the cart
        cartItemRepository.deleteAll(cart.getCartItems());
        log.info("Cart cleared for user {}", user.getUserId());

        return mapToResponse(savedOrder);
    }

    //To get all the orders
    @Override
    public List<ViewOrderResponse> viewOrders(Long userId) {
        // check user
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // fetch orders
        List<Order> orders = orderRepository.findAllByUserUserId(userId);
        // convert Order -> ViewOrderResponse
        return orders.stream()
                .map(order -> {

                    ViewOrderResponse response = new ViewOrderResponse();

                    response.setOrderId(order.getOrderId());
                    response.setTotalAmount(order.getTotalAmount());
                    response.setOrderStatus(order.getStatus());
                    response.setPaymentMethod(order.getPaymentMethod());
                    response.setPaymentStatus(order.getPaymentStatus());
                    response.setShippingAddress(order.getShippingAddress());
                    response.setOrderDate(order.getOrderDate());
                    response.setDeliveryDate(order.getDeliveryDate());

                    List<OrderItemResponse> items = order.getOrderItems()
                            .stream()
                            .map(item -> {

                                OrderItemResponse itemResponse = new OrderItemResponse();

                                itemResponse.setProductId(item.getProduct().getProductId());
                                itemResponse.setProductName(item.getProduct().getName());
                                itemResponse.setQuantity(item.getQuantity());
                                itemResponse.setPrice(item.getPriceAtPurchase());
                                itemResponse.setSubtotal(item.getSubtotal());

                                return itemResponse;

                            })
                            .toList();

                    response.setOrderItems(items);
                    return response;
                })
                .toList();
    }

    @Override
    public String cancelOrder(Long userId) {
        //find the order
        Order order=orderRepository.findById(userId).orElseThrow(()->new ProductNotFoundException("Product Not found"));

        //if order is shipped ,out for delivery ,Delivered,cannot cancel
        if (order.getStatus()== OrderStatus.DELIVERED ||
            order.getStatus()==OrderStatus.SHIPPED||
            order.getStatus()==OrderStatus.OUT_FOR_DELIVERY){
            throw new RuntimeException("Order cannot be cancelled");
        }
        //if already cancelled
        if(order.getStatus()==OrderStatus.CANCELLED){
            throw new RuntimeException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Order is cancelled successfully";
    }


    private OrderResponse mapToResponse(Order order){
        OrderResponse response=new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        return response;
    }








}
