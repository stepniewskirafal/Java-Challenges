package pl.rstepniewski.weatherapp.model.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeteoResponse {
        private double latitude;
        private double longitude;
        private double generationtimeMs;
        private int utc_offset_seconds;
        private String timezone;
        private String timezone_abbreviation;
        private double elevation;
        @JsonProperty("hourly_units")
        private MeteoHourlyUnits hourlyUnits;
        @JsonProperty("hourly")
        private MeteoHourlyScore hourlyScore;
}
