package pl.rstepniewski.internetshop.discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DiscountStrategy {
        BLACK_WEEK_DISCOUNT(prices -> prices.stream()
                .map(price -> price.multiply(BigDecimal.valueOf(0.75)))
                .collect(Collectors.toList())),
        HOLIDAY_DISCOUNT(prices -> prices.stream()
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .collect(Collectors.toList())),
        ZERO_DISCOUNT(prices -> prices);

        private final Function<List<BigDecimal>, List<BigDecimal>> strategy;

        DiscountStrategy(Function<List<BigDecimal>, List<BigDecimal>> strategy) {
                this.strategy = strategy;
        }

        public static Function<List<BigDecimal>, List<BigDecimal>> fromString(String strategy) {
                return switch (strategy) {
                        case "blackWeekDiscount" -> BLACK_WEEK_DISCOUNT.strategy;
                        case "holidayDiscount" -> HOLIDAY_DISCOUNT.strategy;
                        case "zeroDiscount" -> ZERO_DISCOUNT.strategy;
                        default -> throw new IllegalArgumentException("Unknown discount strategy: " + strategy);
                };
        }
}
