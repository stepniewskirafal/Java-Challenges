package pl.rstepniewski.internetshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.internetshop.discount.DiscountService;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;
import pl.rstepniewski.internetshop.repositories.CartRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final DiscountService discountService;
    private final PriceService priceService;

    @Transactional
    public Optional<Cart> getNewCart(UUID cartId){
        return cartRepository.findById(cartId);
    }

    @Transactional
    public Cart addNewCart(Cart cart){
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addProductToCart(Product newProduct, UUID cartID){
        Cart cart = cartRepository.findById(cartID).orElseThrow(NoSuchElementException::new);

        Set<Product> products = cart.getProducts();

        products.stream()
                .filter(p -> p.getName().equals(newProduct.getName()))
                .findFirst()
                .ifPresentOrElse(
                        oldProduct -> oldProduct.setQuantity(oldProduct.getQuantity() + newProduct.getQuantity()),
                        () -> products.add(newProduct)
                );

        cart.setProducts(products);
        cart.setSumPrice(priceService.calculateCartPrice(cart, discountService.discountStrategy()));
        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(UUID cartID){
        cartRepository.deleteById(cartID);
    }
}
