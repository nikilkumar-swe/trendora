package com.project.trendora.service.Impl;

import com.project.trendora.Repository.ProductRepository;
import com.project.trendora.dto.RecommendationResponse;
import com.project.trendora.models.Product;
import com.project.trendora.models.ProductScore;
import com.project.trendora.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final ProductRepository productRepository;

    @Override
    public List<RecommendationResponse> getRecommendations(Long productId) {
        //find curent prod
        Product currentProduct=productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product Not Found"));
        //find all stored products
        List<Product> products=productRepository.findAll();
        //recommendation list
        List<ProductScore> recommendations=new ArrayList<>();

        //find score of each product
        for (Product product:products){
            //skip current product
            if (product.getProductId().equals(productId)){
                continue;
            }
            int score=0;
            //same category
            if (product.getCategory()!=null && product.getCategory().equals(currentProduct.getCategory())){
                score=score+50;
            }
            // Same brand
            if (product.getBrand() != null &&
                    currentProduct.getBrand() != null &&
                    product.getBrand().equalsIgnoreCase(currentProduct.getBrand())) {
                score += 30;
            }
            // Good rating
            if (product.getAverageRating() != null && product.getAverageRating() >= 4.0) {
                score += 10;
            }
            // Sirf useful products add karo
            if (score > 0) {
                recommendations.add(new ProductScore(product, score));
            }
        }
        //  Highest score pehle
        recommendations.sort(Comparator.comparingInt(ProductScore::getScore).reversed() );
        // Top 5 products
        List<RecommendationResponse> result = new ArrayList<>();

        for(int i=0;i<recommendations.size() && i<5 ;i++){
            Product product=recommendations.get(i).getProduct();

            result.add(new RecommendationResponse(
                    product.getProductId(),
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getImageUrl(),
                    product.getAverageRating()
            ));
        }
        return  result;
    }
}
