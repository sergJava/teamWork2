package org.skypro.teamWork2.entity;

import jakarta.persistence.*;
import org.skypro.teamWork2.model.DynamicRecommendationQuery;
import org.skypro.teamWork2.model.enums.QueryType;

import java.util.List;

@Entity
@Table(name = "dynamic_recommendation_query")
public class DynamicRecommendationQueryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dynamic_recommendation_rule_id", nullable = false)
    private DynamicRecommendationRuleEntity parentRule;

    @Enumerated(EnumType.STRING)
    @Column(name = "query_type", nullable = false)
    private QueryType queryType;

    @Column(name = "query_arguments", nullable = false)
    private String packedArguments;

    @Column(name = "should_negate", nullable = false)
    private boolean shouldNegate = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DynamicRecommendationRuleEntity getParentRule() {
        return parentRule;
    }

    public void setParentRule(DynamicRecommendationRuleEntity parentRule) {
        this.parentRule = parentRule;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<String> getArguments() {
        return List.of(this.packedArguments.split(":"));
    }

    public void setArguments(List<String> arguments) {
        this.packedArguments = String.join(":", arguments);
    }

    public boolean isShouldNegate() {
        return shouldNegate;
    }

    public void setShouldNegate(boolean shouldNegate) {
        this.shouldNegate = shouldNegate;
    }

    public DynamicRecommendationQuery toDto() {
        return new DynamicRecommendationQuery(
                this.queryType,
                this.getArguments(),
                this.shouldNegate
        );
    }
}
