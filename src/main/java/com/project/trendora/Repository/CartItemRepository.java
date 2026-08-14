package com.project.trendora.Repository;

import com.project.trendora.models.Cart;
import com.project.trendora.models.CartItems;
import com.project.trendora.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);
    List<CartItems> findByCart(Cart cart);
}
