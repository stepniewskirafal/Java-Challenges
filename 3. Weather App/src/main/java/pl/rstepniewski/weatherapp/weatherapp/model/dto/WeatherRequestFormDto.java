package pl.rstepniewski.weatherapp.weatherapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class WeatherRequestFormDto {
    @NotBlank(message = "Filed 'city' can not be empty.")
    @Size(min = 1, max = 85, message = "Length of city name must be between 1 and 85.")
    private String  city;
    private boolean temperature;
    private boolean wind;
    private boolean rain;
}
