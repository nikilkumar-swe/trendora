package com.project.trendora.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "product name cannot be empty")
    @Size(min =3,max = 30)
    private String name;
    @Column(length = 220)
    @NotBlank(message = "description field cannot be empty")
    private String description;
    @NotBlank(message = "price field cannot be empty")
    @Positive(message = "price should be greater than 0")
    private Double price;
    @PositiveOrZero
    private Integer stockQuantity;
    private String brand;
    private String imageUrl;
    private Boolean available;
    private Double averageRating;
    private Integer totalReviews;
}
