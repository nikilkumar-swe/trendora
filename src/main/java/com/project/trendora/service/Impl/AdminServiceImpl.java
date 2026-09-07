package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.dto.OrderItemResponse;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.exceptions.OrderNotFoundException;
import com.project.trendora.exceptions.ProductNotFoundException;
import com.project.trendora.models.Order;
import com.project.trendora.models.OrderItem;
import com.project.trendora.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final OrderRepository orderRepository;

    public String acceptOrder(Long orderId){
        //find the order
        Order order=orderRepository.findById(orderId).orElseThrow(()->new ProductNotFoundException("Product not found"));

        //we will only confirm placed order
        if(order.getStatus()!= OrderStatus.PLACED){
            throw new RuntimeException("Only placed order can be accepted");
        }
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return "Order is accepted by Trendora";
    }

    @Override
    public OrderResponse processOrder(Long orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        //order must be confirmed before processing
        System.out.println(order.getStatus());
        if(order.getStatus()!=OrderStatus.CONFIRMED){
            throw new RuntimeException("Only confirmed orders can be processed ");
        }
        order.setStatus(OrderStatus.PROCESSING);
        com.project.trendora.models.Order savedOrder =orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponse shipOrder(Long orderId) {

        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
        //order must be processed before shipping
        if (order.getStatus()!=OrderStatus.PROCESSING){
            throw new RuntimeException("Only processed order can be shipped");
        }

        order.setStatus(OrderStatus.SHIPPED);
        Order savedOrder=orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponse orderDelivery(Long orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
        //order must be shipped before delivering
        if (order.getStatus()!=OrderStatus.SHIPPED){
            throw new RuntimeException("Order must be shipped before delivery");
        }
        order.setStatus(OrderStatus.DELIVERED);
        Order savedOrder=orderRepository.save(order);
        return convertToDTO(savedOrder);
    }


    private OrderResponse convertToDTO(Order order) {
        OrderResponse response = new OrderResponse();
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponse dto = new OrderItemResponse();

            dto.setProductId(item.getProduct().getProductId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(item.getSubtotal());
            dto.setPrice(item.getProduct().getPrice());

            // Add dto to the list
            items.add(dto);
        }
        response.setOrderItems(items);
        return response;
    }

}
