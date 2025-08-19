package org.skypro.teamWork2.model.enums;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.BiPredicate;

public enum ComparisonOperator {
    GREATER(">", (a, b) -> a.compareTo(b) > 0),
    LESS("<", (a, b) -> a.compareTo(b) < 0),
    EQUAL("=", (a, b) -> a.compareTo(b) == 0),
    GREATER_OR_EQUAL(">=", (a, b) -> a.compareTo(b) >= 0),
    LESS_OR_EQUAL("<=", (a, b) -> a.compareTo(b) <= 0);

    private final String symbol;
    private final BiPredicate<BigDecimal, BigDecimal> comparator;

    ComparisonOperator(String symbol, BiPredicate<BigDecimal, BigDecimal> comparator) {
        this.symbol = symbol;
        this.comparator = comparator;
    }

    public boolean compare(BigDecimal a, BigDecimal b) {
        return comparator.test(a, b);
    }

    public static ComparisonOperator fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + symbol));
    }

    public String getSymbol() {
        return symbol;
    }
}
