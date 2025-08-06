package org.skypro.teamWork2.entity;

import jakarta.persistence.*;
import org.skypro.teamWork2.model.enums.QueryType;

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


}
