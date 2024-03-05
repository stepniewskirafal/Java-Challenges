package pl.rstepniewski.weatherapp.weatherapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CityDto {
    @Size(message = "Length of city name must be between 1 and 85.", min = 1, max = 85)
    @NotBlank(message = "Filed 'city' can not be empty.")
    private String city;
    private String state;
    private String countryName;
    private String[] timeScore;
    private double[] temperature2mScore;
    private double[] rainScore;
    private double[] windSpeed10mScore;
    private String timeUnit;
    private String temperature2mUnit;
    private String rainUnit;
    private String windSpeed10mUnit;
}