package pl.rstepniewski.weatherapp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.rstepniewski.weatherapp.model.WeatherRequestFrom;
import pl.rstepniewski.weatherapp.model.geocode.GeoPosition;
import pl.rstepniewski.weatherapp.model.geocode.GeocodeResponse;
import pl.rstepniewski.weatherapp.model.openmeteo.ChartDataDto;
import pl.rstepniewski.weatherapp.model.openmeteo.MeteoHourlyScore;
import pl.rstepniewski.weatherapp.model.openmeteo.MeteoResponse;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
public class WeatherService {

    public ChartDataDto getWeatherChartData(WeatherRequestFrom weatherRequestFrom) {
        final MeteoResponse meteoResponse = getWeatherForecast(weatherRequestFrom);

        return new ChartDataDto( meteoResponse.getHourlyUnits(), meteoResponse.getHourlyScore() );
    }

    public MeteoResponse getWeatherForecast(WeatherRequestFrom weatherRequestFrom) {
        final ResponseEntity<GeocodeResponse> geocodeResponse = getCityGeoCode(weatherRequestFrom);
        if (!geocodeResponse.getStatusCode().is2xxSuccessful() && geocodeResponse.getBody() != null) {
            throw new NoSuchElementException();
        }
        GeoPosition geoPosition = getPosition(geocodeResponse);
        final ResponseEntity<MeteoResponse> weatherForecast = getWeatherForecast(geoPosition, weatherRequestFrom);
        return weatherForecast.getBody();
    }

    public GeoPosition getPosition(ResponseEntity<GeocodeResponse> response) {
        GeocodeResponse geocodeResponse = response.getBody();
        double lat = 0;
        double lng = 0;
            if (geocodeResponse.getItems() != null && !geocodeResponse.getItems().isEmpty()) {
                lat = geocodeResponse.getItems().get(0).getPosition().getLat();
                lng = geocodeResponse.getItems().get(0).getPosition().getLng();
            }
        return new GeoPosition(lat, lng);
    }

    private ResponseEntity<GeocodeResponse> getCityGeoCode(WeatherRequestFrom weatherRequestFrom) {
        //https://geocode.search.hereapi.com/v1/geocode?q=Warszawa&apiKey=DkHVGUkIPl-DuPMo_vgiOVP9kze6bIj5snAbAko2V8w
        String apiKey = "DkHVGUkIPl-DuPMo_vgiOVP9kze6bIj5snAbAko2V8w";
        String baseUrl = "https://geocode.search.hereapi.com/v1/geocode";
        String url = baseUrl + "?q=" + weatherRequestFrom.getCity() + "&apiKey=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(url, GeocodeResponse.class);
    }

    private ResponseEntity<MeteoResponse> getWeatherForecast(GeoPosition geoPosition, WeatherRequestFrom weatherRequestFrom) {
        //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,rain,wind_speed_10m
        String baseUrl = "https://api.open-meteo.com/v1/forecast?";
        String url = baseUrl + "latitude=" + geoPosition.getLat() + "&longitude=" + geoPosition.getLng();

        if(weatherRequestFrom.isTemperature()||weatherRequestFrom.isRain()||weatherRequestFrom.isWind()){
            url += "&hourly=";
            if(weatherRequestFrom.isTemperature()){
                url += "temperature_2m";
            }
            if(weatherRequestFrom.isRain()){
                url += ",rain";
            }
            if(weatherRequestFrom.isWind()){
                url += ",wind_speed_10m";
            }
        }

        RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<Object> forEntity = restTemplate.getForEntity(url, Object.class);
        final ResponseEntity<MeteoResponse> restTemplateForEntity = restTemplate.getForEntity(url, MeteoResponse.class);
        return restTemplateForEntity;
    }


}
