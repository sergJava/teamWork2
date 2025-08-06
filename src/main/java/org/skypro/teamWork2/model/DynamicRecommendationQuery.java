package org.skypro.teamWork2.model;

import org.skypro.teamWork2.model.enums.QueryType;

import java.util.List;

public record DynamicRecommendationQuery(
        QueryType queryType,
        List<String> arguments,
        boolean negate
) {}
