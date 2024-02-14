package pl.rstepniewski.internetshop.discount;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountStrategy {
        public static final Function<List<Double>, List<Double>> BLACK_WEEK_DISCOUNT = prices -> prices.stream()
                .map(price -> price * 0.75)
                .collect(Collectors.toList());

        public static final Function<List<Double>, List<Double>> HOLIDAY_DISCOUNT = prices -> prices.stream()
                .map(price -> price * 0.9)
                .collect(Collectors.toList());

        public static final Function<List<Double>, List<Double>> ZERO_DISCOUNT = prices -> prices;
}
