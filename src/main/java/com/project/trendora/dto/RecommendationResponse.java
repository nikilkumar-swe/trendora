package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "RecommendationResponse",
        description = "Response DTO containing recommended product details"
)
public class RecommendationResponse {
    @Schema(
            description = "Unique ID of the recommended product",
            example = "5"
    )
    private Long productId;

    @Schema(
            description = "Name of the recommended product",
            example = "Nike Air Max Shoes"
    )
    private String name;
    @Schema(
            description = "Brand of the recommended product",
            example = "Nike"
    )
    private String brand;


    @Schema(
            description = "Selling price of the recommended product",
            example = "2499.00"
    )
    private Double price;
    @Schema(
            description = "Image URL of the recommended product",
            example = "https://example.com/images/nike-air-max.jpg"
    )
    private String imageUrl;
    @Schema(
            description = "Average customer rating of the recommended product",
            example = "4.5",
            minimum = "0",
            maximum = "5"
    )
    private Double averageRating;

    public RecommendationResponse(
            Long productId,
            String name,
            String brand,
            Double price,
            String imageUrl,
            Double averageRating
    ) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
    }

}
