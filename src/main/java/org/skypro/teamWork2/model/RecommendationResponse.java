package org.skypro.teamWork2.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record RecommendationResponse (
    @JsonProperty("user_id") UUID userId,
    List<ProductRecommendation> recommendations
) {}

