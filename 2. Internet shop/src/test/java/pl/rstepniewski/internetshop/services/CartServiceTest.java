package pl.rstepniewski.internetshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;
import pl.rstepniewski.internetshop.repositories.CartRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartRepository cartRepository;
    private Function<List<Double>, List<Double>> discountStrategy;

    @Autowired
    private CartService cartService;

    UUID cartId;
    Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        discountStrategy = mock(Function.class);
        cartService = new CartService(cartRepository, discountStrategy);

        cartId = UUID.randomUUID();
        cart = new Cart();
    }

    @Test
    void shouldCalculateCartPriceWithNoDiscount() {
        // given
        Product product = new Product();
        product.setPrice(100.0);
        product.setQuantity(1);
        cart.setProducts(Set.of(product));

        when(discountStrategy.apply(any())).thenAnswer(invocation -> invocation.getArgument(0));
        // when
        double price = cartService.calculateCartPrice(cart);
        //then
        assertEquals(100.0, price, "Price should match the product price with no discount applied.");
    }

    @Test
    void shouldCalculateCartPriceWith50Discount() {
        // given
        Product product = new Product();
        product.setPrice(200.0);
        product.setQuantity(2);
        cart.setProducts(Set.of(product));

        when(discountStrategy.apply(any())).thenAnswer(invocation -> {
            List<Double> originalPrices = invocation.getArgument(0);
            return originalPrices.stream()
                    .map(price -> price * 0.5)
                    .collect(Collectors.toList());
        });
        // when
        double price = cartService.calculateCartPrice(cart);
        //then
        assertEquals(200.0, price, "Price should match the discounted price.");
    }

    @Test
    void shouldAddProductToCart_NewProduct() {
        // given
        cart.setProducts(new HashSet<>());

        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setPrice(100.0);
        newProduct.setQuantity(1);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(discountStrategy.apply(any())).thenAnswer(invocation -> invocation.getArgument(0));
        // when
        cartService.addProductToCart(newProduct, cartId);
        //then
        verify(cartRepository).save(any(Cart.class));
        assert cart.getProducts().contains(newProduct);
        assert cart.getSumPrice() == 100.0;
    }

    @Test
    void shouldAddProductToCart_ExistingProduct() {
        // given
        Set<Product> products = new HashSet<>();

        Product existingProduct = new Product();
        existingProduct.setId(UUID.randomUUID());
        existingProduct.setPrice(100.0);
        existingProduct.setQuantity(1);
        products.add(existingProduct);

        cart.setProducts(products);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(discountStrategy.apply(any())).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        cartService.addProductToCart(existingProduct, cartId);

        //then
        verify(cartRepository).save(any(Cart.class));
        assert cart.getProducts().stream().anyMatch(product -> product.getQuantity() == 2);
        assert cart.getSumPrice() == 200.0; // Assuming no discount applied
    }
}
