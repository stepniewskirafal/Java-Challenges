package pl.rstepniewski.internetshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;
import pl.rstepniewski.internetshop.repositories.CartRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final Function<List<Double>, List<Double>> discountStrategy;

    public CartService(CartRepository cartRepository, Function<List<Double>, List<Double>> discountStrategy) {
        this.cartRepository = cartRepository;
        this.discountStrategy = discountStrategy;
    }

    @Transactional
    public void addProductToCart(Product newProduct, UUID cartID){
        Cart cart = cartRepository.findById(cartID).orElseThrow(NoSuchElementException::new);

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
