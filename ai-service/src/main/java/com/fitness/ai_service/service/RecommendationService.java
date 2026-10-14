package com.fitness.ai_service.service;

import com.fitness.ai_service.model.Recommendation;
import com.fitness.ai_service.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
         return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No recommendation found for activityId: " + activityId));
    }
}
