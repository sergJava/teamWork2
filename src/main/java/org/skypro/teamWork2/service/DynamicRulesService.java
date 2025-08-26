package org.skypro.teamWork2.service;

import jakarta.transaction.Transactional;
import org.skypro.teamWork2.entity.DynamicRecommendationQueryEntity;
import org.skypro.teamWork2.entity.DynamicRecommendationRuleEntity;
import org.skypro.teamWork2.model.DynamicRecommendationRule;
import org.skypro.teamWork2.model.DynamicRecommendationRuleIn;
import org.skypro.teamWork2.model.Recommendation;
import org.skypro.teamWork2.model.RuleStat;
import org.skypro.teamWork2.repository.DynamicRecommendationRuleRepository;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.skypro.teamWork2.util.QueryEvaluator;
import org.skypro.teamWork2.util.QueryEvaluatorFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DynamicRulesService {
    private final DynamicRecommendationRuleRepository ruleRepository;
    private final RecommendationsRepository recommendationsRepository;

    public DynamicRulesService(DynamicRecommendationRuleRepository ruleRepository, RecommendationsRepository recommendationsRepository) {
        this.ruleRepository = ruleRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    private List<RuleStat> stats;

    public DynamicRecommendationRule createRule(DynamicRecommendationRuleIn ruleIn) {

        DynamicRecommendationRuleEntity entity = new DynamicRecommendationRuleEntity();
        entity.setProductName(ruleIn.productName());
        entity.setProductId(ruleIn.productId());
        entity.setProductText(ruleIn.text());

        ruleIn.queries().forEach(queryIn -> {
            DynamicRecommendationQueryEntity queryEntity = new DynamicRecommendationQueryEntity();
            queryEntity.setQueryType(queryIn.queryType());
            queryEntity.setArguments(queryIn.arguments());
            queryEntity.setShouldNegate(queryIn.negate());
            entity.addQuery(queryEntity);
        });

        DynamicRecommendationRuleEntity savedEntity = ruleRepository.save(entity);
        return savedEntity.toDto();
    }

    public List<DynamicRecommendationRule> getAllRules() {
        return ruleRepository.findAll().stream()
                .map(DynamicRecommendationRuleEntity::toDto)
                .toList();
    }

    public void deleteRuleByProductId(UUID productId) {
        ruleRepository.deleteByProductId(productId);
    }

    public List<Recommendation> checkDynamicRules(UUID userId) {
        return ruleRepository.findAll().stream()
                .filter(rule -> evaluateRule(userId, rule))
                .peek(rule -> {
                    rule.setHitCount(rule.getHitCount() + 1);
                    ruleRepository.save(rule);
                })
                .map(rule -> new Recommendation(
                        rule.getProductName(),
                        rule.getProductId(),
                        rule.getProductText()
                ))
                .toList();
    }

    private boolean evaluateRule(UUID userId, DynamicRecommendationRuleEntity rule) {
        return rule.getQueries().stream()
                .allMatch(query -> evaluateQuery(userId, query));
    }

    private boolean evaluateQuery(UUID userId, DynamicRecommendationQueryEntity query) {
        QueryEvaluator evaluator = QueryEvaluatorFactory.create(
                query.getQueryType(),
                query.getArguments(),
                query.isShouldNegate()
        );
        return evaluator.evaluate(userId, recommendationsRepository);
    }

    public RuleStatsResponse getStats() {
        List<RuleStat> stats = ruleRepository.findAll().stream()
                .map(rule -> new RuleStat(
                        rule.getId(),
                        rule.getHitCount()
                ))
                .toList();
        return new RuleStatsResponse(stats);
    }

    //record для Response
    public record RuleStatsResponse(List<RuleStat> stats) {}
}