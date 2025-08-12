package org.skypro.teamWork2.util;

import org.skypro.teamWork2.model.enums.QueryType;
import org.skypro.teamWork2.util.evaluators.ActiveUserOfQuery;
import org.skypro.teamWork2.util.evaluators.TransactionSumCompareDepositWithdrawQuery;
import org.skypro.teamWork2.util.evaluators.TransactionSumCompareQuery;
import org.skypro.teamWork2.util.evaluators.UserOfQuery;

import java.util.List;

public class QueryEvaluatorFactory {
    public static QueryEvaluator create(QueryType queryType, List<String> arguments, boolean shouldNegate) {
        return switch (queryType) {
            case USER_OF -> new UserOfQuery(arguments, shouldNegate);
            case ACTIVE_USER_OF -> new ActiveUserOfQuery(arguments, shouldNegate);
            case TRANSACTION_SUM_COMPARE -> new TransactionSumCompareQuery(arguments, shouldNegate);
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> new TransactionSumCompareDepositWithdrawQuery(arguments, shouldNegate);
        };
    }
}
