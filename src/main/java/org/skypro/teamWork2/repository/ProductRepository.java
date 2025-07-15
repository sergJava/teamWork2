package org.skypro.teamWork2.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public boolean userHasProductOfType(UUID userId, String productType) {
        String sql = """
                SELECT COUNT(*) > 0
                FROM transaction t
                INNER JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ?
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType);
    }

    public BigDecimal getTotalDepositsByProductType(UUID userId, String productType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transaction t
                INNER JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = "DEPOSIT"
                """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, productType);
    }

    public BigDecimal getTotalExpensesByProductType(UUID userId, String productType) {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transaction t
                INNER JOIN products p ON t.product_id = p.id
                WHERE t.user_id = ? AND p.type = ? AND t.type = "WITHDRAW"
                """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, productType);
    }
}
