package com.project.trendora.controller;

import com.project.trendora.dto.RecommendationResponse;
import com.project.trendora.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Slf4j
public class RecommendationController {
    private final RecommendationService recommendationService;

    public  RecommendationController(RecommendationService recommendationService){
        this.recommendationService=recommendationService;
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(@PathVariable Long productId){
        log.info("get product recommendations request received productId={}",productId);
        List<RecommendationResponse> recommendations=recommendationService.getRecommendations(productId);
        return ResponseEntity.ok(recommendations);
    }

}
