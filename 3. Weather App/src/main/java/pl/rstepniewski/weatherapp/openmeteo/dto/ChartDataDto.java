package pl.rstepniewski.weatherapp.openmeteo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyScore;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoHourlyUnits;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChartDataDto {
    private String city;
    private String state;
    private String countryName;
    private MeteoHourlyUnits hourlyUnits;
    private MeteoHourlyScore hourlyScore;
}
