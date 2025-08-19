package org.skypro.teamWork2.repository;

import org.skypro.teamWork2.entity.DynamicRecommendationRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DynamicRecommendationRuleRepository extends JpaRepository<DynamicRecommendationRuleEntity, Long> {
    void deleteByProductId(UUID productId);

    @Modifying
    @Query("UPDATE DynamicRecommendationRuleEntity r SET r.hitCount = r.hitCount + 1 WHERE r.id = :id")
    void incrementHitCount(@Param("id") Long Id);
}
