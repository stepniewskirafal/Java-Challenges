# Internet Shop Cart System

Welcome to my Internet Shop project documentation!
This project focuses on creating a functional shopping cart system that allows adding products to a cart and calculating the total cart value with the flexibility of applying different discount strategies based on events.

## Project Overview

The project is structured around several key components:

- **Cart and Product Models**: At the heart of our system are the `Cart` and `Product` classes, representing the shopping cart and the items within it, respectively.
- **Discount Strategy Implementation**: A unique approach is taken for discount strategies, where the name of the strategy is retrieved from a dedicated database table, and the process is cached for efficiency. If the table lacks a value, a default configuration from the YML file is used. Based on this, a corresponding Lambda function for the discount strategy is returned.
- **Configuration and Caching**: To ensure performance and scalability, we use Caffeine caching for our configuration values, including discount strategies.

## Key Components

### Configuration and Caching

We employ a caching mechanism to optimize the retrieval of configuration values, including discount strategies. Here's how we set up our caching with Caffeine:

```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .initialCapacity(1)
                .maximumSize(10);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
```

## Discount Strategy
Our discount strategy logic is encapsulated within the DiscountStrategy class, which utilizes Lambda expressions for different discount scenarios:

```java
public class DiscountStrategy {
    public static final Function<List<Double>, List<Double>> BLACK_WEEK_DISCOUNT = prices -> prices.stream()
            .map(price -> price * 0.75)
            .collect(Collectors.toList());

    public static final Function<List<Double>, List<Double>> HOLIDAY_DISCOUNT = prices -> prices.stream()
            .map(price -> price * 0.9)
            .collect(Collectors.toList());

    public static final Function<List<Double>, List<Double>> ZERO_DISCOUNT = prices -> prices;
}
```

## Cart Service
The CartService class is responsible for adding products to the cart and calculating the cart's total price, considering any applicable discounts.