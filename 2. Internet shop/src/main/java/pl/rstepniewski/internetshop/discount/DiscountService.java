package pl.rstepniewski.internetshop.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.internetshop.config.ConfigParameterService;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final ConfigParameterService service;
    private final static String DISCOUNT_STRATEGY = "DISCOUNT_STRATEGY";

    public Function<List<BigDecimal>, List<BigDecimal>> discountStrategy() {
        String strategy = service.getConfigValue(DISCOUNT_STRATEGY);
            return switch (strategy) {
            case "blackWeekDiscount" -> DiscountStrategy.BLACK_WEEK_DISCOUNT;
            case "holidayDiscount" -> DiscountStrategy.HOLIDAY_DISCOUNT;
            case "zeroDiscount" -> DiscountStrategy.ZERO_DISCOUNT;
            default -> throw new IllegalArgumentException("Unknown discount strategy: " + strategy);
        };
    }
}
