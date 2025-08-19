package org.skypro.teamWork2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public record DynamicRecommendationRule(
        Long id,
        @JsonProperty("product_name") String productName,
        @JsonProperty("product_id") UUID productId,
        @JsonProperty("product_text") String productText,
        @JsonProperty("rule") List<DynamicRecommendationQuery> queries
) {}
