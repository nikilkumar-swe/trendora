package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Schema(
        name = "ProductResponse",
        description = "Response DTO containing complete product details, inventory, ratings and timestamps"
)
public class ProductResponse {

    @Schema(
            description = "Unique ID of the product",
            example = "1"
    )
    private Long productId;
    @Schema(
            description = "Name of the product",
            example = "Nike Air Max Shoes"
    )
    private String name;

    @Column(length = 220)
    @Schema(
            description = "Detailed description of the product",
            example = "Comfortable running shoes with lightweight cushioning",
            maxLength = 220
    )
    private String description;
    @Schema(
            description = "Selling price of the product",
            example = "2499.00"
    )
    private Double price;
    @Schema(
            description = "Available stock quantity",
            example = "50"
    )
    private Integer stockQuantity;
    @Schema(
            description = "Brand or manufacturer of the product",
            example = "Nike"
    )
    private String brand;
    @Schema(
            description = "URL of the product image",
            example = "https://example.com/images/nike-air-max.jpg"
    )
    private String imageUrl;
    @Schema(
            description = "Indicates whether the product is available for purchase",
            example = "true"
    )
    private Boolean available;
    @Schema(
            description = "Average customer rating of the product",
            example = "4.5"
    )
    private Double averageRating;
    @Schema(
            description = "Total number of customer reviews",
            example = "120"
    )
    private Integer totalReviews;

    @CreationTimestamp
    @Schema(
            description = "Date and time when the product was created",
            example = "2026-10-13T10:30:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(
            description = "Date and time when the product was last updated",
            example = "2026-10-13T12:45:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime updatedAt;
}
