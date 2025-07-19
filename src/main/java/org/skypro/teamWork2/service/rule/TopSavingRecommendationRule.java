package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.model.ProductRecommendation;
import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.RecommendedProduct;
import org.skypro.teamWork2.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRecommendationRule implements RecommendationRule {
    private static final Logger logger = LoggerFactory.getLogger(TopSavingRecommendationRule.class);
    private final ProductRepository productRepository;
    private static final BigDecimal MIN_DEPOSIT_THRESHOLD = new BigDecimal("50000");

    public TopSavingRecommendationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductRecommendation> check(UUID userId) {
        logger.debug("Checking TopSaving rule for user: {}", userId);
        boolean usesDebit = productRepository.userHasProductOfType(userId, ProductType.DEBIT);
        BigDecimal debitDeposits = productRepository.getTotalDepositsByProductType(userId, ProductType.DEBIT);
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, ProductType.SAVING);
        BigDecimal debitExpenses = productRepository.getTotalExpensesByProductType(userId, ProductType.DEBIT);

        boolean condition1 = debitDeposits.compareTo(MIN_DEPOSIT_THRESHOLD)>=0 ||
                savingDeposits.compareTo(MIN_DEPOSIT_THRESHOLD)>=0;
        boolean condition2 = debitDeposits.compareTo(debitExpenses)>0;
        if (usesDebit && condition1 && condition2) {
            logger.info("Recommending Top Saving for user: {}", userId);
            return Optional.of(RecommendedProduct.TOP_SAVING.getDto());
        }
        return Optional.empty();
    }
}
