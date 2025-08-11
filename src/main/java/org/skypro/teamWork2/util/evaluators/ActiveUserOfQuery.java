package org.skypro.teamWork2.util.evaluators;

import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.skypro.teamWork2.util.QueryEvaluator;

import java.util.List;
import java.util.UUID;

public class ActiveUserOfQuery implements QueryEvaluator {
    private final ProductType productType;
    private final boolean shouldNegate;

    public ActiveUserOfQuery(List<String> arguments, boolean shouldNegate) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("ACTIVE_USER_OF query expects 1 argument");
        }
        this.productType = ProductType.valueOf(arguments.get(0));
        this.shouldNegate = shouldNegate;
    }

    @Override
    public boolean evaluate(UUID userId, RecommendationsRepository repository) {
        boolean result = repository.isActiveUserOfProductType(userId, productType);
        return shouldNegate ? !result : result;
    }
}
