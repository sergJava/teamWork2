package org.skypro.teamWork2.model.enums;

import org.skypro.teamWork2.model.ProductDescriptions;
import org.skypro.teamWork2.model.ProductRecommendation;

import java.util.UUID;

public enum RecommendedProduct {
    INVEST_500(
            "Invest500",
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            ProductDescriptions.INVEST_500
    ),
    TOP_SAVING(
            "Top Saving",
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            ProductDescriptions.TOP_SAVING
    ),
    SIMPLE_CREDIT(
            "Simple credit",
            UUID.fromString("1f9b149c-6577-448a-bc94-16bea229b71a"),
            ProductDescriptions.SIMPLE_CREDIT
    );


    private final String name;
    private final UUID recommendationId;
    private final String description;

    RecommendedProduct(String name, UUID recommendationId, String description) {
        this.name = name;
        this.recommendationId = recommendationId;
        this.description = description;
    }

    public ProductRecommendation getDto(){
        return new ProductRecommendation(name, recommendationId, description);
    }

    public String getName() {
        return name;
    }

    public UUID getRecommendationId() {
        return recommendationId;
    }

    public String getDescription() {
        return description;
    }
}
