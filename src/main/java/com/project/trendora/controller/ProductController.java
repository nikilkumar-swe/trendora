package com.project.trendora.controller;

import com.project.trendora.dto.ProductRequest;
import com.project.trendora.dto.ProductResponse;
import com.project.trendora.models.Product;
import com.project.trendora.service.Impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product APIs",description = "operational APIs for product")
public class ProductController {

    private final ProductServiceImpl productService;

    @Operation(summary = "Add a product")
    @ApiResponses({
        @ApiResponse(responseCode = "201",description = "Product added successfully"),
            @ApiResponse(responseCode ="400",description = "Bad request ")
    }
    )
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }

    @Operation(summary = "get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Product found "),
            @ApiResponse(responseCode = "404",description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductInfo(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @Operation(summary = "get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",description = "Products not found")
    })

    @GetMapping("/get/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "productId") String sortBy,
                                                                @RequestParam(defaultValue = "asc") String sortDir
                                                                ){

        return ResponseEntity.ok(
                productService.getAllProducts(page,size,sortBy,sortDir)
        );

    }

    @Operation(summary = "Delete product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Product deleted successfully "),
            @ApiResponse(responseCode = "404",description = "Product not found")
    })
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @Operation(summary = "update product")
    @PatchMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestParam Long id,@Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateProduct(id,productRequest));
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }

}
