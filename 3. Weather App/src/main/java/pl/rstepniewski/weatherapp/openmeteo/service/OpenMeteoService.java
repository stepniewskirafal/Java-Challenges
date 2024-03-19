package pl.rstepniewski.weatherapp.openmeteo.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.rstepniewski.weatherapp.openmeteo.exception.ApiOpenMeteoException;
import pl.rstepniewski.weatherapp.openmeteo.model.dto.MeteoResponseDto;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;
import pl.rstepniewski.weatherapp.geocode.model.GeoPosition;

@Service
public class OpenMeteoService {

    private final RestClient restClient;

    public OpenMeteoService() {
        this.restClient = RestClient.create();
    }

    @Retryable(value = {HttpClientErrorException.class, HttpServerErrorException.class, HttpStatusCodeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public MeteoResponseDto getWeatherForecast(final GeoPosition geoPosition, final WeatherRequestFormDto weatherRequestFormDto) {
        final String uri = buildUrl(geoPosition, weatherRequestFormDto);

        final MeteoResponseDto meteoResponse = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), (request, response) -> {
                        throw new ApiOpenMeteoException("4xxClientError");
                    })
                    .onStatus(status -> status.is5xxServerError(), (request, response) -> {
                        throw new ApiOpenMeteoException("5xxClientError");
                    })
                    .body(MeteoResponseDto.class);

        if (meteoResponse == null) {
            throw new RuntimeException("Received null response from weather service");
        }
        return meteoResponse;
    }

    private String buildUrl(final GeoPosition geoPosition, final WeatherRequestFormDto weatherRequestFormDto) {
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", geoPosition.getLat())
                .queryParam("longitude", geoPosition.getLng())
                .queryParam("hourly", getParams(weatherRequestFormDto));
        return uriBuilder.toUriString();
    }

    private static StringBuilder getParams(final WeatherRequestFormDto weatherRequestFormDto) {
        final StringBuilder hourlyParams = new StringBuilder(34);
        if(weatherRequestFormDto.isTemperature()) {
            hourlyParams.append("temperature_2m");
        }
        if(weatherRequestFormDto.isRain()) {
            if(!hourlyParams.isEmpty()) {
                hourlyParams.append(',');
            }
            hourlyParams.append("rain");
        }
        if(weatherRequestFormDto.isWind()) {
            if(!hourlyParams.isEmpty()){
                hourlyParams.append(',');
            }
            hourlyParams.append("wind_speed_10m");
        }
        return hourlyParams;
    }
}
