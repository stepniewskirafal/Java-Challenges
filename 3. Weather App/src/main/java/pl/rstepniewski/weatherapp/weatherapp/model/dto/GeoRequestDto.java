package pl.rstepniewski.weatherapp.weatherapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoRequestDto {
    private String city;
    private String county;
    private String state;
    private double lat;
    private double lng;
}
