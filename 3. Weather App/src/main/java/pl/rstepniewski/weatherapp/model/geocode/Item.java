package pl.rstepniewski.weatherapp.model.geocode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    private String title;
    private String id;
    private String resultType;
    private String localityType;
    private GeoAddress address;
    private GeoPosition position;
    private GeoMapView mapView;
    private GeoScoring scoring;

}
