package com.project.trendora.service;


import com.project.trendora.dto.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    List<RecommendationResponse> getRecommendations(Long productId);


}
