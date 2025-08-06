package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.model.enums.RecommendedProduct;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.skypro.teamWork2.repository.ProductRepository;
import org.skypro.teamWork2.model.ProductRecommendation;
import org.skypro.teamWork2.model.enums.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RecommendationRule implements RecommendationRule {
    private static final Logger logger = LoggerFactory.getLogger(Invest500RecommendationRule.class);
    private final ProductRepository productRepository;

    public Invest500RecommendationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Optional<ProductRecommendation> check(UUID userId) {
        logger.debug("Checking Invest500 rule for user: {}", userId);
        boolean usesDebit = productRepository.isUserOfProductType(userId, ProductType.DEBIT);
        boolean usesInvest = productRepository.isUserOfProductType(userId, ProductType.INVEST);
        BigDecimal savingDeposits = productRepository.sunAmountsForUserAndType(userId, ProductType.SAVING, TransactionType.DEPOSIT);

        if(usesDebit && !usesInvest && savingDeposits.compareTo(new BigDecimal(1000))>0){
            logger.info("Recommending Invest500 for user: {}", userId);
            return Optional.of(RecommendedProduct.INVEST_500.getDto());
        }
        return Optional.empty();
    }
}
