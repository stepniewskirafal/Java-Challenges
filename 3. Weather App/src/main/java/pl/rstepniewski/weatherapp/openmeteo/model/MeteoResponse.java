package pl.rstepniewski.weatherapp.openmeteo.model;

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
        @JsonProperty("generationtimeMs")
        private double generationTimeMs;
        @JsonProperty("utc_offset_seconds")
        private int utcOffsetSeconds;
        private String timezone;
        @JsonProperty("timezone_abbreviation")
        private String timezoneAbbreviation;
        private double elevation;
        @JsonProperty("hourly_units")
        private MeteoHourlyUnits hourlyUnits;
        @JsonProperty("hourly")
        private MeteoHourlyScore hourlyScore;
}
