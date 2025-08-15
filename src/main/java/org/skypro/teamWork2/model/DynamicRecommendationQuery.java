package org.skypro.teamWork2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skypro.teamWork2.model.enums.QueryType;

import java.util.List;

public record DynamicRecommendationQuery(
        @JsonProperty("query") QueryType queryType,
        @JsonProperty("arguments") List<String> arguments,
        @JsonProperty("negate") boolean negate
) {}
