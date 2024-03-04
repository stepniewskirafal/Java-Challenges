package pl.rstepniewski.weatherapp.geocode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoPosition {
    private double lat;
    private double lng;
}
