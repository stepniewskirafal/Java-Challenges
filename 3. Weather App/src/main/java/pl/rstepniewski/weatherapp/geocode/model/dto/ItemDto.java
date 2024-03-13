package pl.rstepniewski.weatherapp.geocode.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rstepniewski.weatherapp.geocode.model.GeoAddress;
import pl.rstepniewski.weatherapp.geocode.model.GeoPosition;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    private String title;
    private GeoAddress address;
    private GeoPosition position;
}
