package pl.rstepniewski.internetshop.services;

import org.springframework.stereotype.Service;
import pl.rstepniewski.internetshop.model.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PriceService {

    public BigDecimal calculateCartPrice(Cart cart, Function<List<BigDecimal>, List<BigDecimal>> strategy) {
        final List<BigDecimal> productPrices = cart.getProducts().stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .collect(Collectors.toList());

        List<BigDecimal> discountedPrices = strategy.apply(productPrices);
        return discountedPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
