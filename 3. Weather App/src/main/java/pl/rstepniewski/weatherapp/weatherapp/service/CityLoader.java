package pl.rstepniewski.weatherapp.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.rstepniewski.weatherapp.weatherapp.model.City;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;
import pl.rstepniewski.weatherapp.weatherapp.repository.CityRepository;

@Component
@RequiredArgsConstructor
public class CityLoader implements CommandLineRunner {

    private final CityRepository repository;

    @Override
    public void run(String... args) throws Exception {
        //WeatherRequestFormDto weatherRequestFormDto
        //repository.save(new City());
    }
}