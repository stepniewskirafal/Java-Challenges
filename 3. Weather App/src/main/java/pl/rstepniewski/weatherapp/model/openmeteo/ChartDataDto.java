package pl.rstepniewski.weatherapp.model.openmeteo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
