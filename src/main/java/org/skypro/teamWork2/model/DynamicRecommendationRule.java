package org.skypro.teamWork2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public record DynamicRecommendationRule(
        Long id,
        String productName,
        UUID productId,
        String productText,
        @JsonProperty("rule") List<DynamicRecommendationQuery> queries
) {}
