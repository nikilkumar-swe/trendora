package com.project.trendora.controller;

import com.project.trendora.dto.OrderRequest;
import com.project.trendora.dto.OrderResponse;
import com.project.trendora.dto.ViewOrderResponse;
import com.project.trendora.service.Impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Order Management",
        description = "APIs for placing, viewing and cancelling customer orders"
)
public class OrderController {

    private final OrderServiceImpl orderService;

    @Operation(
            summary = "Place a new order",
            description = "Places a new order for a customer using the provided order details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    @PostMapping("/place/{userId}")
    public ResponseEntity<OrderResponse> placeOrder(@Parameter(description = "User ID", example = "1", required = true)
                                                        @Valid @RequestBody OrderRequest orderRequest){
        log.info("place order request received");
        OrderResponse response=orderService.placeOrder(orderRequest);
        log.info("Order placed successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "View user orders",
            description = "Retrieves all orders placed by a specific customer."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    //get all orders of a user
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<ViewOrderResponse>> viewOrder(@Parameter(description = "User ID", example = "1", required = true)
                                                                 @PathVariable Long userId){
        log.info("view order request received userId={}",userId);
        List<ViewOrderResponse> orders=orderService.viewOrders(userId);
        log.info("Order retrieved successfully userId={} count={}",userId,orders.size());
        return ResponseEntity.ok(orders);

    }

    @Operation(
            summary = "Cancel order",
            description = "Cancels an existing order if cancellation is allowed."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Order cannot be cancelled"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder( @Parameter(description = "Order ID", example = "1", required = true)
                                                   @PathVariable Long orderId){
       log.info("Cancel order request received orderId={}",orderId);
        java.lang.String response =orderService.cancelOrder(orderId);
        log.info("Order cancelled successfully orderId={}",orderId);
        return ResponseEntity.ok(response);
    }

}
