package org.skypro.teamWork2.recommendationRules;

import org.skypro.teamWork2.repository.ProductRepository;
import org.skypro.teamWork2.util.ProductDescriptions;
import org.skypro.teamWork2.util.ProductRecommendation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RecommendationRule implements RecommendationRule {
    private final ProductRepository productRepository;

    public Invest500RecommendationRule(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Optional<ProductRecommendation> check(UUID userId) {
        boolean usesDebit = productRepository.userHasProductOfType(userId, "DEBIT");
        boolean usesInvest = productRepository.userHasProductOfType(userId, "INVEST");
        BigDecimal savingDeposits = productRepository.getTotalDepositsByProductType(userId, "SAVING");

        if(usesDebit && usesInvest && savingDeposits.compareTo(new BigDecimal(1000))>0){
            return Optional.of(new ProductRecommendation("Invest500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    ProductDescriptions.INVEST_500));
        }
        return Optional.empty();
    }
}
