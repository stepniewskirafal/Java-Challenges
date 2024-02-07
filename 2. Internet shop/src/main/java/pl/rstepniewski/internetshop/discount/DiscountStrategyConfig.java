package pl.rstepniewski.internetshop.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rstepniewski.internetshop.config.ConfigurationService;

import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class DiscountStrategyConfig {

    private final ConfigurationService service;
    private final static String DISCOUNT_STRATEGY = "DISCOUNT_STRATEGY";

    @Bean
    public Function<List<Double>, List<Double>> discountStrategy() {
        final String strategy = service.getConfigValue(DISCOUNT_STRATEGY);
        return switch (strategy) {
            case "blackWeekDiscount" -> DiscountStrategy.BLACK_WEEK_DISCOUNT;
            case "holidayDiscount" -> DiscountStrategy.HOLIDAY_DISCOUNT;
            case "zeroDiscount" -> DiscountStrategy.ZERO_DISCOUNT;
            default -> throw new IllegalArgumentException("Unknown discount strategy: " + strategy);
        };
    }
}