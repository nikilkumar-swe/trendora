package com.project.trendora.dto;

import lombok.Data;

@Data
public class RecommendationResponse {
    private Long productId;
    private String name;
    private String brand;
    private Double price;
    private String imageUrl;
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
