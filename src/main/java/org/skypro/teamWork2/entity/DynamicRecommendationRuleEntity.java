package org.skypro.teamWork2.entity;

import jakarta.persistence.*;
import org.skypro.teamWork2.model.DynamicRecommendationQuery;
import org.skypro.teamWork2.model.DynamicRecommendationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dynamic_recommendation_rule")
public class DynamicRecommendationRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false, columnDefinition = "text")
    private String productText;

    @Column(name = "hit_count", nullable = false)
    private int hitCount = 0;

    @OneToMany(mappedBy = "parentRule",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<DynamicRecommendationQueryEntity> queries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public List<DynamicRecommendationQueryEntity> getQueries() {
        return queries;
    }

    public void setQueries(List<DynamicRecommendationQueryEntity> queries) {
        this.queries = queries;
    }

    public void addQuery(DynamicRecommendationQueryEntity query) {
        queries.add(query);
        query.setParentRule(this);
    }

    public void removeQuery(DynamicRecommendationQueryEntity query ) {
        queries.remove(query);
        query.setParentRule(null);
    }

    public DynamicRecommendationRule toDto() {
        return new DynamicRecommendationRule(
                this.id,
                this.productName,
                this.productId,
                this.productText,
                this.queries.stream()
                        .map(DynamicRecommendationQueryEntity::toDto)
                        .toList()
        );
    }
}
