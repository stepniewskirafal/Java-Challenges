package pl.rstepniewski.weatherapp.openmeteo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyScore;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyUnits;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeteoResponseDto {
    private double latitude;
    private double longitude;
    @JsonProperty("hourly_units")
    private MeteoHourlyUnits hourlyUnits;
    @JsonProperty("hourly")
    private MeteoHourlyScore hourlyScore;
}
