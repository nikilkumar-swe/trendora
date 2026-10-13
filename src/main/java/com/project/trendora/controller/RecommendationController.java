package com.project.trendora.controller;

import com.project.trendora.dto.RecommendationResponse;
import com.project.trendora.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Slf4j
@Tag(
        name = "Product Recommendations",
        description = "APIs for retrieving recommended products based on a selected product"
)
public class RecommendationController {
    private final RecommendationService recommendationService;

    public  RecommendationController(RecommendationService recommendationService){
        this.recommendationService=recommendationService;
    }
    @Operation(
            summary = "Get product recommendations",
            description = "Retrieves a list of recommended products based on the given product ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product recommendations retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations( @Parameter(
            description = "Unique ID of the product for which recommendations are requested", example = "1",
            required = true
    )@PathVariable Long productId){

        log.info("get product recommendations request received productId={}",productId);
        List<RecommendationResponse> recommendations=recommendationService.getRecommendations(productId);
        return ResponseEntity.ok(recommendations);
    }

}
