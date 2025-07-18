package org.skypro.teamWork2.model;


import java.util.List;
import java.util.UUID;

public record RecommendationResponse (
    UUID userId,
    List<ProductRecommendation> recommendations
) {}

