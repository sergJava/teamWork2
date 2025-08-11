package org.skypro.teamWork2.util.evaluators;

import org.skypro.teamWork2.model.enums.ComparisonOperator;
import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.skypro.teamWork2.util.QueryEvaluator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionSumCompareDepositWithdrawQuery implements QueryEvaluator {
    private final ProductType productType;
    private final ComparisonOperator operator;
    private final boolean shouldNegate;

    public TransactionSumCompareDepositWithdrawQuery(List<String> arguments, boolean shouldNegate) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW query expects 2 arguments");
        }
        this.productType = ProductType.valueOf(arguments.get(0));
        this.operator = ComparisonOperator.valueOf(arguments.get(1));
        this.shouldNegate = shouldNegate;
    }

    @Override
    public boolean evaluate(UUID userId, RecommendationsRepository repository) {
        BigDecimal depositAmount = repository.sumAmountsForUserAndType(userId, productType, TransactionType.DEPOSIT);
        BigDecimal withdrawAmount = repository.sumAmountsForUserAndType(userId, productType, TransactionType.WITHDRAW);

        boolean result = operator.compare(depositAmount, withdrawAmount);
        return shouldNegate ? !result : result;
    }
}
