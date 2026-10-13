package com.project.trendora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "ProductRequest",
        description = "Request DTO for creating or updating product details in Trendora"
)
public class ProductRequest {
    @Schema(
            description = "Name of the product",
            example = "Nike Air Max Shoes",
            minLength = 3,
            maxLength = 30,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "product name cannot be empty")
    @Size(min =3,max = 30)
    private String name;

    @Schema(
            description = "Detailed description of the product",
            example = "Comfortable running shoes with lightweight cushioning",
            maxLength = 220,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Column(length = 220)
    @NotBlank(message = "description field cannot be empty")
    private String description;

    @Schema(
            description = "Selling price of the product",
            example = "2499.00",
            minimum = "0",
            exclusiveMinimum = true,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "price field cannot be empty")
    @Positive(message = "price should be greater than 0")
    private Double price;

    @Schema(
            description = "Available stock quantity",
            example = "50",
            minimum = "0"
    )
    @PositiveOrZero
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
            example = "4.5",
            minimum = "0",
            maximum = "5"
    )
    private Double averageRating;

    @Schema(
            description = "Total number of customer reviews",
            example = "120",
            minimum = "0"
    )
    private Integer totalReviews;
}
