package com.project.trendora.controller;

import com.project.trendora.dto.OrderResponse;
import com.project.trendora.service.Impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@Tag(
        name = "Admin Order Management",
        description = "Admin APIs for managing order lifecycle: accept, process, ship and deliver"
)
public class AdminController {

    private final AdminServiceImpl adminServices;

    public AdminController(AdminServiceImpl adminServices){
        this.adminServices=adminServices;
    }

    @Operation(
            summary = "Accept order",
            description = "Allows admin to accept an order and update its status to CONFIRMED."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order status"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/orders/accept/{orderId}")
    public ResponseEntity<String> acceptOrder( @Parameter(description = "Order ID", example = "1")
                                                   @PathVariable Long orderId){

        log.info("Admin requested order acceptance orderId={}", orderId);
        String response=adminServices.acceptOrder(orderId);
        log.info("Order accepted successfully orderId={}", orderId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Process order",
            description = "Moves an accepted order to the processing stage."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order status transition"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/orders/{orderId}/process")
    public ResponseEntity<OrderResponse> OrderProcessing( @Parameter(description = "Order ID", example = "1")
                                                              @PathVariable Long orderId){
        log.info("Admin requested order processing orderId={}", orderId);
        OrderResponse order=adminServices.processOrder(orderId);
        log.info("Order processed successfully orderId={}", orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Ship order",
            description = "Marks a processed order as shipped."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order shipped successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order status transition"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/orders/{orderId}/ship")
    public  ResponseEntity<OrderResponse> orderShipping( @Parameter(description = "Order ID", example = "1")
                                                             @PathVariable Long orderId){
        log.info("Admin requested order shipping orderId={}",orderId);
        OrderResponse order=adminServices.shipOrder(orderId);
        log.info("Order shipped successfully orderId={}",orderId);
        return ResponseEntity.ok().body(order);
    }
    @Operation(
            summary = "Deliver order",
            description = "Marks a shipped order as delivered."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order delivered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order status transition"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/orders/{orderId}/deliver")
    public ResponseEntity<OrderResponse> isDelivered( @Parameter(description = "Order ID", example = "1")
                                                          @PathVariable Long orderId){
        log.info("Admin requested order delivering orderId={}",orderId);
        OrderResponse order=adminServices.orderDelivery(orderId);
        log.info("Order delivered successfully orderId={}",orderId);
        return ResponseEntity.ok().body(order);

    }



}
