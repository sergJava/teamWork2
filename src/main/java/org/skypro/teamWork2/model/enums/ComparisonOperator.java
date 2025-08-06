package org.skypro.teamWork2.model.enums;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum ComparisonOperator {
    GREATER(">", (a, b) -> a > b),
    LESS("<", (a, b) -> a < b),
    EQUAL("=", (a, b) -> a == b),
    GREATER_OR_EQUAL(">=", (a, b) -> a >= b),
    LESS_OR_EQUAL("<=", (a, b) -> a <= b);

    private final String symbol;
    private final BiPredicate<Long, Long> comparator;

    ComparisonOperator(String symbol, BiPredicate<Long, Long> comparator) {
        this.symbol = symbol;
        this.comparator = comparator;
    }

    public boolean compare(long a, long b) {
        return comparator.test(a, b);
    }

    public static ComparisonOperator fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + symbol));
    }
}
