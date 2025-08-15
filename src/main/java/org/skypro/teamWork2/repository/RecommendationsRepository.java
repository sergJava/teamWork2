package org.skypro.teamWork2.repository;

import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public boolean isUserOfProductType(UUID userId, ProductType productType) {
        String sql = """
                SELECT COUNT(*) > 0
                FROM TRANSACTIONS t
                INNER JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        return jdbcTemplate.queryForObject(sql,
                Boolean.class,
                userId,
                productType.getDbValue()
        );
    }

    public boolean isActiveUserOfProductType(UUID userId, ProductType productType) {
        String sql = """
                SELECT COUNT(*) >= 5 FROM TRANSACTIONS t
                INNER JOIN products p ON t.product = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType.name());
    }

    public BigDecimal sumAmountsForUserAndType(UUID userId, ProductType productType, TransactionType transactionType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM TRANSACTIONS t
                INNER JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = ?
                """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, productType.getDbValue(), transactionType.getDbValue());
    }
}
