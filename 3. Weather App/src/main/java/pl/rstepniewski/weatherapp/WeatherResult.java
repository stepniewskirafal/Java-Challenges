package pl.rstepniewski.weatherapp;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WeatherResult {
    private double[] temp;
    private double[] rain;
    private double[] wind;
}
