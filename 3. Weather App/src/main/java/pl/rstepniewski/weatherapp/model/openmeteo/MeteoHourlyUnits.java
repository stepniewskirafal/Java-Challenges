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
public class MeteoHourlyUnits {
    private String time;
    @JsonProperty("temperature_2m")
    private String temperature2m;
    private String rain;
    @JsonProperty("wind_speed_10m")
    private String windSpeed10m;
}