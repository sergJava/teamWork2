package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.model.ProductRecommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<ProductRecommendation> check(UUID userId);
}
