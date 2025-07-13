package org.skypro.teamWork2.recommendationRules;

import org.skypro.teamWork2.util.ProductRecommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RecommendationRule implements RecommendationRule {


    @Override
    public Optional<ProductRecommendation> check(UUID id) {
        return Optional.empty();
    }
}
