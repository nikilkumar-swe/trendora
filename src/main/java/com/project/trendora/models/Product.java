package com.project.trendora.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotBlank(message = "product name cannot be blank")
    @Size(min = 3,max = 30)
    private String name;
    @Column(length = 220)
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private String brand;
    private String imageUrl;
    private Boolean available;
    private Double averageRating;
    private Integer totalReviews;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    // Orders me ye product kitni baar aaya hai
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    // Carts me ye product kitni baar add hua hai
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;
}
