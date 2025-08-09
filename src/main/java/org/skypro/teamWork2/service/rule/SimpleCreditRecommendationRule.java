package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.model.ProductRecommendation;
import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.RecommendedProduct;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.skypro.teamWork2.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRecommendationRule implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCreditRecommendationRule.class);
    private final ProductRepository productRepository;
    private static final BigDecimal MIN_DEPOSIT_THRESHOLD = new BigDecimal("100000");

    public SimpleCreditRecommendationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductRecommendation> check(UUID userId) {
        logger.debug("Checking SimpleCredit rule for user: {}", userId);
        boolean usesCredit = productRepository.isUserOfProductType(userId, ProductType.CREDIT);
        BigDecimal debitDeposits = productRepository.sumAmountsForUserAndType(userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        BigDecimal debitExpenses = productRepository.sumAmountsForUserAndType(userId, ProductType.DEBIT, TransactionType.WITHDRAW);
//        BigDecimal debitExpenses = productRepository.getTotalExpensesByProductType(userId, ProductType.DEBIT);

        boolean condition1 = debitDeposits.compareTo(debitExpenses)>0;
        boolean condition2 = debitDeposits.compareTo(MIN_DEPOSIT_THRESHOLD)>=0;
        if (!usesCredit && condition1 && condition2) {
            logger.info("Recommending Simple Credit for user: {}", userId);
            return Optional.of(RecommendedProduct.SIMPLE_CREDIT.getDto());
        }
        return Optional.empty();
    }

}
