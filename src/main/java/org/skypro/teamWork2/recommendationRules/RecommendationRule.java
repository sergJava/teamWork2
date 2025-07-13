package org.skypro.teamWork2.recommendationRules;

import org.skypro.teamWork2.util.ProductRecommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRule {
    Optional<ProductRecommendation> check(UUID id);
}
