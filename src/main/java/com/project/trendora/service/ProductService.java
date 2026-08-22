package com.project.trendora.service;

import com.project.trendora.dto.ProductRequest;
import com.project.trendora.dto.ProductResponse;
import com.project.trendora.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse getProduct(Long id);
    Page<ProductResponse> getAllProducts(int page,int size,String sortBy,String sortDir);
    String deleteProduct(Long id);
    ProductResponse updateProduct(Long id,ProductRequest productRequest);
    List<Product> searchProducts(String keyword);

}
