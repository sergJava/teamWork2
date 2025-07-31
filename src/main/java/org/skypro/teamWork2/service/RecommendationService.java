package org.skypro.teamWork2.service;

import org.skypro.teamWork2.service.rule.RecommendationRule;
import org.skypro.teamWork2.repository.ProductRepository;
import org.skypro.teamWork2.model.ProductRecommendation;
import org.skypro.teamWork2.model.RecommendationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRule> recommendationRules;

    public RecommendationService(List<RecommendationRule> recommendationRules) {
        this.recommendationRules = recommendationRules;
    }

    public RecommendationResponse getRecommendations(UUID userId) {
        List<ProductRecommendation> recommendations = recommendationRules.stream()
                .map(rule -> rule.check(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return new RecommendationResponse(userId, recommendations);
    }
}
