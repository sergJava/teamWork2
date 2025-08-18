package org.skypro.teamWork2.service;

import org.skypro.teamWork2.model.Recommendation;
import org.skypro.teamWork2.model.RecommendationResponse;
import org.skypro.teamWork2.service.rule.RecommendationRuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> staticRules;
    private final DynamicRulesService dynamicRulesService;
    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(List<RecommendationRuleSet> staticRules, DynamicRulesService dynamicRulesService) {
        this.staticRules = staticRules;
        this.dynamicRulesService = dynamicRulesService;
    }

    public RecommendationResponse getRecommendations(UUID userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        staticRules.forEach(rule -> rule.check(userId).ifPresent(recommendations::add));
        recommendations.addAll(dynamicRulesService.checkDynamicRules(userId));

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Recommendations for user {} fetched in {} ms", userId, duration);

        return new RecommendationResponse(userId, recommendations);
    }
}
