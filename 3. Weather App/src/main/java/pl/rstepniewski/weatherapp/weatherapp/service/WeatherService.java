package pl.rstepniewski.weatherapp.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.weatherapp.geocode.model.GeoPosition;
import pl.rstepniewski.weatherapp.weatherapp.model.City;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.CityDto;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.CityMapper;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;
import pl.rstepniewski.weatherapp.geocode.model.GeocodeResponse;
import pl.rstepniewski.weatherapp.geocode.service.GeocodeService;
import pl.rstepniewski.weatherapp.openmeteo.model.MeteoResponse;
import pl.rstepniewski.weatherapp.openmeteo.service.OpenMeteoService;
import pl.rstepniewski.weatherapp.weatherapp.repository.CityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final OpenMeteoService openMeteoService;
    private final GeocodeService geocodeService;
    private final CityRepository cityRepository;


    public CityDto getWeatherChartData(final WeatherRequestFormDto weatherRequestFormDto) {
        final Optional<City> cityOptional = cityRepository.findByCity(weatherRequestFormDto.getCity());
        CityDto weatherChartData = null;

        if(cityOptional.isPresent()){
            final City city = cityOptional.get();
            final CityDto cityDto = CityMapper.toDto(city);
            weatherChartData = applyRequestFilters(cityDto, weatherRequestFormDto);
        }else{
            final GeocodeResponse cityGeoCode = geocodeService.getCityGeoCode(weatherRequestFormDto.getCity());
            final GeoPosition cityPosition = extractPosition(cityGeoCode);
            final MeteoResponse meteoResponse = openMeteoService.getWeatherForecast(cityPosition, weatherRequestFormDto);

            weatherChartData = createWeatherChartData(meteoResponse, cityGeoCode);
        }

        return weatherChartData;
    }

    private CityDto applyRequestFilters(final CityDto cityDto, final WeatherRequestFormDto weatherRequestFormDto) {
        if(!weatherRequestFormDto.isTemperature()){
            cityDto.setTemperature2mScore(null);
        }
        if(!weatherRequestFormDto.isRain()){
            cityDto.setRainScore(null);
        }
        if(!weatherRequestFormDto.isWind()){
            cityDto.setWindSpeed10mScore(null);
        }
        return cityDto;
    }

    private CityDto createWeatherChartData(final MeteoResponse meteoResponse, final GeocodeResponse geocodeResponse) {
        final String[] cityTitle = geocodeResponse.getItems()
                .get(0)
                .getTitle()
                .replaceAll(" ", "")
                .split(",");
        final String cityName = cityTitle[0];
        final String cityState = cityTitle[1];
        final String cityCountryName = cityTitle[2];

        return CityDto.builder()
                .city(cityName)
                .state(cityState)
                .countryName(cityCountryName)
                .timeScore(meteoResponse.getHourlyScore().getTime())
                .temperature2mScore(meteoResponse.getHourlyScore().getTemperature2m())
                .rainScore(meteoResponse.getHourlyScore().getRain())
                .windSpeed10mScore(meteoResponse.getHourlyScore().getWindSpeed10m())
                .timeUnit(meteoResponse.getHourlyUnits().getTime())
                .temperature2mUnit(meteoResponse.getHourlyUnits().getTemperature2m())
                .rainUnit(meteoResponse.getHourlyUnits().getRain())
                .windSpeed10mUnit(meteoResponse.getHourlyUnits().getWindSpeed10m())
                .build();
    }

    private GeoPosition extractPosition(final GeocodeResponse geocode) {
        final double lat = geocode.getItems().get(0).getPosition().getLat();
        final double lng = geocode.getItems().get(0).getPosition().getLng();

        return new GeoPosition(lat, lng);
    }

}
