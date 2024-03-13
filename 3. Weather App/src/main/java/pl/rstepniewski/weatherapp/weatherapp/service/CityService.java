package pl.rstepniewski.weatherapp.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.weatherapp.weatherapp.model.City;
import pl.rstepniewski.weatherapp.weatherapp.repository.CityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    public Optional<City> findByCity(String city) {
        return repository.findByCityIgnoreCase(city);
    }

    public void save(City entity) {
        repository.save(entity);
    }
}
