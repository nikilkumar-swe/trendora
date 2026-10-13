package com.project.trendora.service.Impl;

import com.project.trendora.Repository.ProductRepository;
import com.project.trendora.dto.RecommendationResponse;
import com.project.trendora.exceptions.ProductNotFoundException;
import com.project.trendora.models.Product;
import com.project.trendora.models.ProductScore;
import com.project.trendora.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final ProductRepository productRepository;

    @Override
    public List<RecommendationResponse> getRecommendations(Long productId) {
        log.info("Fetching recommendations | productId={}", productId);

        Product currentProduct=productRepository.findById(productId)
                .orElseThrow(()->{
                    log.warn("Product not found | productId={}", productId);
                    return new ProductNotFoundException("Product not found");
                });

        //find all stored products
        List<Product> products=productRepository.findAll();
        log.debug("Total products fetched | count={}", products.size());

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

        log.debug("Matching products found | count={}", recommendations.size());

        //  Highest score pehle
        recommendations.sort(Comparator.comparingInt(ProductScore::getScore).reversed() );

        // Top 5 products
        List<RecommendationResponse> result = new ArrayList<>();

        for(int i=0 ; i<recommendations.size() && i<5 ;i++){

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
        log.info("Recommendations generated successfully | productId={} | count={}", productId, result.size());
        return  result;
    }
}
