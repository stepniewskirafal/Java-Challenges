package pl.rstepniewski.internetshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;
import pl.rstepniewski.internetshop.model.User;
import pl.rstepniewski.internetshop.repositories.CartRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final Function<List<Double>, List<Double>> discountStrategy;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addProductToCart(Product newProduct, Long userId){
        User user = userService.findById(userId).orElseThrow(NoSuchElementException::new);
        Cart cart = user.getCart();

         Set<Product> products = cart.getProducts();
         if(products.contains(newProduct)){
             Product product = products.stream().filter(newProduct::equals).findAny().orElseThrow(NoSuchElementException::new);
             product.setQuantity(product.getQuantity()+1);
             products.add(product);
         }else{
             products.add(newProduct);
         }
        cart.setProducts(products);
        cart.setSumPrice(calculateCartPrice(cart));
        cartRepository.save(cart);
    }

    public double calculateCartPrice(Cart cart) {
        final List<Double> productPrices = cart.getProducts().stream()
                .map(product -> product.getPrice() * product.getQuantity())
                .collect(Collectors.toList());

        List<Double> discountedPrices = discountStrategy.apply(productPrices);
        return discountedPrices.stream().mapToDouble(Double::doubleValue).sum();
    }
}
