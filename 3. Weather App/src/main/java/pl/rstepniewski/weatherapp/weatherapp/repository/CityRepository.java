package pl.rstepniewski.weatherapp.weatherapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.rstepniewski.weatherapp.weatherapp.model.City;

import java.util.UUID;

public interface CityRepository extends CrudRepository<City, UUID> {
}
