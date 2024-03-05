package pl.rstepniewski.weatherapp.weatherapp.model.dto;

import pl.rstepniewski.weatherapp.weatherapp.model.City;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final  class CityMapper {

    private CityMapper() {
    }

    public static CityDto toDto(final City city) {
        if (city == null) {
            return null;
        }

        return CityDto.builder()
                .city(city.getCity())
                .state(city.getState())
                .countryName(city.getCountryName())
                .timeScore(city.getTimeScore().toArray(new String[0])) // Przekształcanie listy na tablicę
                .temperature2mScore(listToArray(city.getTemperature2mScore()))
                .rainScore(listToArray(city.getRainScore()))
                .windSpeed10mScore(listToArray(city.getWindSpeed10mScore()))
                .timeUnit(city.getTimeUnit())
                .temperature2mUnit(city.getTemperature2mUnit())
                .rainUnit(city.getRainUnit())
                .windSpeed10mUnit(city.getWindSpeed10mUnit())
                .build();
    }

    public static City toEntity(final CityDto cityDto) {
        if (cityDto == null) {
            return null;
        }

        final City city = new City();
        city.setCity(cityDto.getCity());
        city.setState(cityDto.getState());
        city.setCountryName(cityDto.getCountryName());
        city.setTimeScore(Arrays.asList(cityDto.getTimeScore())); // Przekształcanie tablicy na listę
        city.setTemperature2mScore(arrayToList(cityDto.getTemperature2mScore()));
        city.setRainScore(arrayToList(cityDto.getRainScore()));
        city.setWindSpeed10mScore(arrayToList(cityDto.getWindSpeed10mScore()));
        city.setTimeUnit(cityDto.getTimeUnit());
        city.setTemperature2mUnit(cityDto.getTemperature2mUnit());
        city.setRainUnit(cityDto.getRainUnit());
        city.setWindSpeed10mUnit(cityDto.getWindSpeed10mUnit());
        return city;
    }

    private static List<Double> arrayToList(final double... array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    private static double[] listToArray(final List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
