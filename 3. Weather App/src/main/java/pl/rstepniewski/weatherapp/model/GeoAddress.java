package pl.rstepniewski.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoAddress {
    private String label;
    private String countryCode;
    private String countryName;
    private String state;
    private String county;
    private String city;
    private String postalCode;
}
