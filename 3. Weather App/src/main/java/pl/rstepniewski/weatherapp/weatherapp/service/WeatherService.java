package pl.rstepniewski.weatherapp.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;
import pl.rstepniewski.weatherapp.geocode.model.GeocodeResponse;
import pl.rstepniewski.weatherapp.geocode.service.GeocodeService;
import pl.rstepniewski.weatherapp.openmeteo.dto.ChartDataDto;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoResponse;
import pl.rstepniewski.weatherapp.openmeteo.service.OpenMeteoService;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final OpenMeteoService openMeteoService;
    private final GeocodeService geocodeService;

    public ChartDataDto getWeatherChartData(WeatherRequestFormDto weatherRequestFormDto) {
        final GeocodeResponse geocodeResponse = geocodeService.getCityGeoCode(weatherRequestFormDto.getCity());
        final MeteoResponse meteoResponse = openMeteoService.getWeatherForecast(geocodeResponse, weatherRequestFormDto);

        return createWeatherChartData(meteoResponse, geocodeResponse);
    }

    private ChartDataDto createWeatherChartData(MeteoResponse meteoResponse, GeocodeResponse geocodeResponse) {
        return ChartDataDto.builder()
                .city(geocodeResponse.getItems().get(0).getAddress().getCity())
                .state(geocodeResponse.getItems().get(0).getAddress().getState())
                .countryName(geocodeResponse.getItems().get(0).getAddress().getCountryName())
                .hourlyUnits(meteoResponse.getHourlyUnits())
                .hourlyScore(meteoResponse.getHourlyScore())
                .build();
    }



}
