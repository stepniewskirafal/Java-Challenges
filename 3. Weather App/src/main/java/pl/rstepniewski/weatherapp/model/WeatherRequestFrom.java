package pl.rstepniewski.weatherapp.model;

import jakarta.validation.constraints.NotNull;
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
    //@NotNull(message = "City can not be null")
    private String city;
    private boolean Temperature;
    private boolean Wind;
    private boolean Rain;
}
