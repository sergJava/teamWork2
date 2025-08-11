package org.skypro.teamWork2.util;

import org.skypro.teamWork2.model.enums.QueryType;

import java.util.List;

public class QueryEvaluatorFactory {
    public static QueryEvaluator create(QueryType queryType, List<String> arguments, boolean shouldNegate) {
        return switch (queryType) {
            case USER_OF -> new UserOf
        }
    }
}
