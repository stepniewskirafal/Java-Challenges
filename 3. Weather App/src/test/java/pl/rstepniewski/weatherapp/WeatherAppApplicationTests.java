package pl.rstepniewski.weatherapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherAppApplicationTests {

    @Test
    void contextLoads() {
        enum OwnEnum {
            A, B, C
        }
        assertEquals("A", OwnEnum.A.name());
    }

}
