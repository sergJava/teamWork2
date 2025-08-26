package org.skypro.teamWork2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RuleStat(
        @JsonProperty("rule_id") Long ruleId,
        @JsonProperty("count") int hitCount
) {
}
