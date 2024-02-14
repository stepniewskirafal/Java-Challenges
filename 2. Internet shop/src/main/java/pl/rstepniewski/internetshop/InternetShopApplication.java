package pl.rstepniewski.internetshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InternetShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternetShopApplication.class, args);
    }
}
