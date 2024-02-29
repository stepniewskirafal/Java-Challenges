package pl.rstepniewski.weatherapp.model.openmeteo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChartDataDto {
    private MeteoHourlyUnits hourlyUnits;
    private MeteoHourlyScore hourlyScore;
}
