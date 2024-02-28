package pl.rstepniewski.weatherapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class WeatherRequestFrom {
    private String city;
    private boolean Temperature;
    private boolean Wind;
    private boolean Rain;

}
