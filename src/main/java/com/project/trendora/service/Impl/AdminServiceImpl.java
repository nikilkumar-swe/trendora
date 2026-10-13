package com.project.trendora.service.Impl;

import com.project.trendora.Repository.OrderRepository;
import com.project.trendora.dto.OrderItemResponse;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.enums.OrderStatus;
import com.project.trendora.exceptions.OrderNotFoundException;
import com.project.trendora.models.Order;
import com.project.trendora.models.OrderItem;
import com.project.trendora.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final OrderRepository orderRepository;

    public String acceptOrder(Long orderId){
        log.info("Accept order request received | orderId={}", orderId);
        //find the order
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));

        //we will only confirm placed order
        if(order.getStatus()!= OrderStatus.PLACED){
            log.warn("Order acceptance failed | orderId={} | status={}", orderId, order.getStatus());
            throw new IllegalStateException("Only placed orders can be accepted");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        log.info("Order accepted successfully | orderId={} | status=CONFIRMED", orderId);
        return "Order is accepted by Trendora";
    }

    @Override
    public OrderResponse processOrder(Long orderId) {
        log.info("Process order request received | orderId={}", orderId);

        //find order
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));

        //order must be confirmed before processing
        log.debug("Current order status | orderId={} | status={}", orderId, order.getStatus());

        if(order.getStatus()!=OrderStatus.CONFIRMED){
            log.warn("Order processing failed | orderId={} | status={}", orderId, order.getStatus());
            throw new IllegalStateException("Only confirmed orders can be processed");
        }

        order.setStatus(OrderStatus.PROCESSING);
        com.project.trendora.models.Order savedOrder =orderRepository.save(order);

        log.info("Order processing started | orderId={} | status=PROCESSING", orderId);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponse shipOrder(Long orderId) {

        log.info("Ship order request received | orderId={}", orderId);
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order not found"));

        //order must be processed before shipping
        if (order.getStatus()!=OrderStatus.PROCESSING){
            log.warn("Order shipping failed | orderId={} | status={}",
                    orderId, order.getStatus());
            throw new IllegalStateException("Only processed orders can be shipped");
        }

        order.setStatus(OrderStatus.SHIPPED);
        Order savedOrder=orderRepository.save(order);

        log.info("Order shipped successfully | orderId={} | status=SHIPPED", orderId);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponse orderDelivery(Long orderId) {
        log.info("Order delivery request received | orderId={}", orderId);

        //find order
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Order not found"));

        //order must be shipped before delivering
        if (order.getStatus()!=OrderStatus.SHIPPED){
            log.warn("Order delivery failed | orderId={} | status={}",
                    orderId, order.getStatus());
            throw new IllegalStateException("Order must be shipped before delivery");
        }

        order.setStatus(OrderStatus.DELIVERED);
        Order savedOrder=orderRepository.save(order);
        log.info("Order delivered successfully | orderId={} | status=DELIVERED", orderId);
        return convertToDTO(savedOrder);
    }


    private OrderResponse convertToDTO(Order order) {
        log.debug("Converting order to DTO | orderId={}", order.getOrderId());

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
