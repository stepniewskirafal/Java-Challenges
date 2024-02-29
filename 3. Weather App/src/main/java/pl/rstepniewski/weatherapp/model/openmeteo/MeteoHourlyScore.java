package pl.rstepniewski.weatherapp.model.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeteoHourlyScore {
    private String[] time;
    @JsonProperty("temperature_2m")
    private double[] temperature2m;
    private double[] rain;
    @JsonProperty("wind_speed_10m")
    private double[] windSpeed10m;
}
