package pl.rstepniewski.internetshop.discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountStrategy {
        public static final Function<List<BigDecimal>, List<BigDecimal>> BLACK_WEEK_DISCOUNT = prices -> prices.stream()
                .map(price -> price.multiply(BigDecimal.valueOf(0.75)))
                .collect(Collectors.toList());

        public static final Function<List<BigDecimal>, List<BigDecimal>> HOLIDAY_DISCOUNT = prices -> prices.stream()
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)) )
                .collect(Collectors.toList());

        public static final Function<List<BigDecimal>, List<BigDecimal>> ZERO_DISCOUNT = prices -> prices;
}
