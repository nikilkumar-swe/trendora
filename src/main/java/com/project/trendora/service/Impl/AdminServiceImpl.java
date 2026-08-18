package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.exceptions.ProductNotFoundException;
import com.project.trendora.models.Order;
import com.project.trendora.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
