package pl.rstepniewski.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoScoring {
    private double queryScore;
    private GeoFieldScore fieldScore;
}
