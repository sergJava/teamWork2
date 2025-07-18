package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.repository.ProductRepository;
import org.skypro.teamWork2.model.ProductDescriptions;
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
        boolean usesDebit = productRepository.userHasProductOfType(userId, ProductType.DEBIT);
        boolean usesInvest = productRepository.userHasProductOfType(userId, ProductType.INVEST);
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, ProductType.SAVING);

        if(usesDebit && !usesInvest && savingDeposits.compareTo(new BigDecimal(1000))>0){
            logger.info("Recommending Invest500 for user: {}", userId);
            return Optional.of(new ProductRecommendation(
                    "Invest500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    ProductDescriptions.INVEST_500));
        }
        return Optional.empty();
    }
}
