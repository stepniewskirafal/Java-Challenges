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
    private final static String CHOSEN_STRATEGY = "DISCOUNT_STRATEGY";

    public Function<List<BigDecimal>, List<BigDecimal>> discountStrategy() {
        final String configValue = service.getConfigValue(CHOSEN_STRATEGY);
        return DiscountStrategy.fromString(configValue);
    }
}
