package org.skypro.teamWork2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ResponseWrapper(
        @JsonProperty("data") List<DynamicRecommendationRule> rules
) {
}
