package pl.rstepniewski.internetshop.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.rstepniewski.internetshop.config.ConfigParameter;
import pl.rstepniewski.internetshop.config.ConfigParameterService;
import pl.rstepniewski.internetshop.discount.DiscountService;
import pl.rstepniewski.internetshop.model.Cart;
import pl.rstepniewski.internetshop.model.Product;

import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class CartServiceTest {
    @Autowired
    private ConfigParameterService configParameterService;
    @Autowired
    private CartService cartService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private DiscountService discountService;
    private final static String CONFIG_KEY_STRATEGY = "DISCOUNT_STRATEGY";
    private UUID cartId;
    private Cart cart;
    private Product productFirst;
    private Product productSecond;
    @BeforeEach
    void setUp() {
        cart = new Cart();
        productFirst = new Product();
        productSecond = new Product();

        productFirst.setPrice(BigDecimal.valueOf(100.0));
        productFirst.setQuantity(1);
        productFirst.setName("First product name");

        productSecond.setPrice(BigDecimal.valueOf(200.0));
        productSecond.setQuantity(2);
        productSecond.setName("Second product name");
        cartId = cart.getId();
    }
    @AfterEach
    public void tearDown() {
        configParameterService.cacheEvict();
        cartService.deleteCart(cartId);
    }
    @Test
    void shouldCalculateOneProductCartPriceWithNoDiscount() {
        // given
        configParameterService.save(new ConfigParameter(CONFIG_KEY_STRATEGY, "zeroDiscount"));

        cart.setProducts(Set.of(productFirst));
        // when
        BigDecimal price = priceService.calculateCartPrice(cart, discountService.discountStrategy());
        //then
        assertTrue("Price should match the product price with no discount applied.",
                BigDecimal.valueOf(100).compareTo(price) == 0);
    }
    @Test
    void shouldCalculateTwoProductsCartPriceWithNoDiscount() {
        // given
        configParameterService.save(new ConfigParameter(CONFIG_KEY_STRATEGY, "zeroDiscount"));

        cart.setProducts(Set.of(productFirst, productSecond));
        // when
        BigDecimal price = priceService.calculateCartPrice(cart, discountService.discountStrategy());
        //then
        assertTrue("Price should match the product price with no discount applied.",
                BigDecimal.valueOf(500).compareTo(price) == 0);
    }
    @Test
    void shouldCalculateOneProductCartPriceWith10PercentDiscount() {
        // given
        configParameterService.save(new ConfigParameter(CONFIG_KEY_STRATEGY, "holidayDiscount"));

        cart.setProducts(Set.of(productFirst));
        // when
        BigDecimal price = priceService.calculateCartPrice(cart, discountService.discountStrategy());
        //then
        assertTrue("Price should match the product price with 10% discount applied.",
                BigDecimal.valueOf(90).compareTo(price) == 0);
    }
    @Test
    void shouldCalculateOneProductCartPriceWith25PercentDiscount() {
        // given
        configParameterService.save(new ConfigParameter(CONFIG_KEY_STRATEGY, "blackWeekDiscount"));

        cart.setProducts(Set.of(productFirst));
        // when
        BigDecimal price = priceService.calculateCartPrice(cart, discountService.discountStrategy());
        //then
        assertTrue("Price should match the product price with 25% discount applied.",
                BigDecimal.valueOf(75).compareTo(price) == 0);
    }
    @Test
    void shouldAddOneProductToCart() {
        // given

        // when
        cartService.addNewCart(cart);
        final Cart savedCart = cartService.addProductToCart(productFirst, cartId);

        final Optional<Product> productOptional = savedCart.getProducts().stream().filter(p -> p.equals(productFirst)).findAny();
        //then
        assertEquals("Only one product should be added to the cart", 1, savedCart.getProducts().size());
        assertTrue( "Product should be added to the cart", productOptional.isPresent());
    }
}
