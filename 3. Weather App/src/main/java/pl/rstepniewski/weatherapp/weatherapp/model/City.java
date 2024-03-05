package pl.rstepniewski.weatherapp.weatherapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @NotBlank(message = "Filed 'city' can not be empty.")
    @Size(min = 1, max = 85, message = "Length of city name must be between 1 and 85.")
    private String city;
    private String state;
    private String countryName;
    @ElementCollection
    @CollectionTable(name = "city_time_score", joinColumns = @JoinColumn(name = "city_id"))
    @Column(name = "time_score")
    private List<String> timeScore = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "city_temperature_2m_score", joinColumns = @JoinColumn(name = "city_id"))
    @Column(name = "temperature_2m_score")
    private List<Double> temperature2mScore = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "city_rain_score", joinColumns = @JoinColumn(name = "city_id"))
    @Column(name = "rain_score")
    private List<Double> rainScore = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "city_wind_speed_10m_score", joinColumns = @JoinColumn(name = "city_id"))
    @Column(name = "wind_speed_10m_score")
    private List<Double> windSpeed10mScore = new ArrayList<>();
    private String timeUnit;
    private String temperature2mUnit;
    private String rainUnit;
    private String windSpeed10mUnit;

    public City() {
        this.id = UUID.randomUUID();
    }
}
