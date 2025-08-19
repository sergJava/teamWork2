package org.skypro.teamWork2.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    private final Cache<CacheKey, Boolean> userProductTypeCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(10_000)
            .build();

    private final Cache<CacheKey, BigDecimal> transactionSumCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(10_000)
            .build();

    private final Cache<CacheKey, Boolean> activeUserProductTypeCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(10_000)
            .build();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private record CacheKey(UUID userId, String arg1, String arg2) {
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public boolean isUserOfProductType(UUID userId, ProductType productType) {
        CacheKey key = new CacheKey(userId, productType.getDbValue(), null);

        return userProductTypeCache.get(key, k -> {
            String sql = """
                    SELECT COUNT(*) > 0
                    FROM TRANSACTIONS t
                    INNER JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                    """;
            return jdbcTemplate.queryForObject(sql,
                    Boolean.class,
                    k.userId(),
                    k.arg1()
            );
        });

    }

    public boolean isActiveUserOfProductType(UUID userId, ProductType productType) {
        CacheKey key = new CacheKey(userId, productType.getDbValue(), null);

        return activeUserProductTypeCache.get(key, k -> {
            String sql = """
                    SELECT COUNT(*) >= 5 FROM TRANSACTIONS t
                    INNER JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                    """;
            return jdbcTemplate.queryForObject(sql,
                    Boolean.class,
                    k.userId(),
                    k.arg1()
            );
        });
    }

    public BigDecimal sumAmountsForUserAndType(UUID userId, ProductType productType, TransactionType transactionType) {
        CacheKey key = new CacheKey(userId, productType.getDbValue(), transactionType.getDbValue());

        return transactionSumCache.get(key, k -> {
            String sql = """
                    SELECT COALESCE(SUM(t.amount), 0)
                    FROM TRANSACTIONS t
                    INNER JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ? AND t.type = ?
                    """;
            return jdbcTemplate.queryForObject(sql,
                    BigDecimal.class,
                    k.userId(),
                    k.arg1(),
                    k.arg2()
            );
        });
    }
}
