package pl.rstepniewski.internetshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.internetshop.discount.DiscountService;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;
import pl.rstepniewski.internetshop.repositories.CartRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final DiscountService discountService;

    @Transactional
    public Cart addNewCart(Cart cart){
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addProductToCart(Product newProduct, UUID cartID){
        Cart cart = cartRepository.findById(cartID).orElseThrow(NoSuchElementException::new);

         Set<Product> products = cart.getProducts();
         if(products.contains(newProduct)){
             Product product = products.stream().filter(newProduct::equals).findAny().orElseThrow(NoSuchElementException::new); //equals bez ID i ilość
             product.setQuantity(product.getQuantity()+1);
             products.add(product);
         }else{
             products.add(newProduct);
         }
        cart.setProducts(products);
        cart.setSumPrice(calculateCartPrice(cart));
        return cartRepository.save(cart);
    }

    public BigDecimal calculateCartPrice(Cart cart) { //schować metodę           // osobna klasa na weściu cart i strategia
        final List<BigDecimal> productPrices = cart.getProducts().stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .collect(Collectors.toList());

        List<BigDecimal> discountedPrices = discountService.discountStrategy().apply(productPrices);
        return discountedPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
