package com.project.trendora.Repository;

import com.project.trendora.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);

    List<Product> findByCategoryAndProductIdNot(
            String category,
            Long productId
    );

    List<Product> findByBrandAndProductIdNot(
            String brand,
            Long productId
    );

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name,
            String description,
            String category
    );
}
