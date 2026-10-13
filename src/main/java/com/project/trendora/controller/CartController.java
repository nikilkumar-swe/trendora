    package com.project.trendora.controller;

    import com.project.trendora.dto.AddToCartRequest;
    import com.project.trendora.dto.RemoveFromCartRequest;
    import com.project.trendora.dto.ViewCartResponse;
    import com.project.trendora.service.Impl.CartServiceImpl;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/cart")
    @RequiredArgsConstructor
    @Slf4j
    @Tag(
            name = "Cart Management",
            description = "APIs for adding, removing and viewing cart products"
    )
    public class CartController {

        private final CartServiceImpl cartService;

        @Operation(
                summary = "Add product to cart",
                description = "Adds a product to the user's shopping cart."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Product added successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
                @ApiResponse(responseCode = "404", description = "User or product not found")
        })
        @PostMapping("/add")
        public ResponseEntity<String> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest){
            log.info("Add to cart request received");
            String response=cartService.addToCart(addToCartRequest);
            log.info("Product added to cart successfully");
            return ResponseEntity.ok(response);
        }

        @Operation(
                summary = "Remove product from cart",
                description = "Removes a product from the user's shopping cart."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Product removed successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request data"),
                @ApiResponse(responseCode = "404", description = "Cart item not found")
        })
        //remove from cart
        @PostMapping("/remove")
        public ResponseEntity<String> removeFromCart(@Valid @RequestBody RemoveFromCartRequest request){
            log.info("Remove to cart request received ");
            String response=cartService.removeFromCart(request);
            log.info("Product removed successfully");
            return ResponseEntity.ok(response);
        }

        @Operation(
                summary = "View user cart",
                description = "Retrieves all products available in the user's shopping cart."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
                @ApiResponse(responseCode = "404", description = "User or cart not found")
        })
        @GetMapping("/view/{userId}")
        public ResponseEntity<ViewCartResponse> viewCart(@PathVariable Long userId){
            log.info("View cart request received userId={}", userId);
            ViewCartResponse response=cartService.viewCart(userId);
            log.info("Cart retrieved successfully userId={}", userId);
            return ResponseEntity.ok(response);
        }

    }
