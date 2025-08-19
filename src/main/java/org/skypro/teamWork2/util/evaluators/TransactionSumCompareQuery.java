package org.skypro.teamWork2.util.evaluators;

import org.skypro.teamWork2.model.enums.ComparisonOperator;
import org.skypro.teamWork2.model.enums.ProductType;
import org.skypro.teamWork2.model.enums.TransactionType;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.skypro.teamWork2.util.QueryEvaluator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionSumCompareQuery implements QueryEvaluator {
    private final ProductType productType;
    private final TransactionType transactionType;
    private final ComparisonOperator operator;
    private final BigDecimal compareValue;
    private final boolean shouldNegate;

    public TransactionSumCompareQuery(List<String> arguments, boolean shouldNegate) {
        if (arguments.size() != 4) {
            throw new IllegalArgumentException("TRANSACTION_SUM_COMPARE query expects 4 arguments");
        }
        this.productType = ProductType.valueOf(arguments.get(0));
        this.transactionType = TransactionType.valueOf(arguments.get(1));
        this.operator = ComparisonOperator.fromSymbol(arguments.get(2));
        this.compareValue = new BigDecimal(arguments.get(3));
        this.shouldNegate = shouldNegate;
        if (compareValue.compareTo(BigDecimal.valueOf(0.0)) < 0) {
            throw new IllegalArgumentException("the value cannot be negative.");
        }
    }

    @Override
    public boolean evaluate(UUID userId, RecommendationsRepository repository) {
        BigDecimal transactionsSum = repository.sumAmountsForUserAndType(userId, productType, transactionType);
        boolean result = operator.compare(transactionsSum, compareValue);
        return shouldNegate ? !result : result;
    }

    @Override
    public String toString() {
        return String.format(
                "TransactionSumCompareQuery[type=%s, transaction=%s, operator=%s, value=%s, negate=%b]",
                productType, transactionType, operator.getSymbol(), compareValue, shouldNegate
        );
    }
}
