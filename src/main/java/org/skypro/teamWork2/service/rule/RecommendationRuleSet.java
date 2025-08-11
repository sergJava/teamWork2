package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> check(UUID userId);
}
