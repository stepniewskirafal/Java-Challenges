package pl.rstepniewski.internetshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.internetshop.config.ConfigParameter;

@Repository
public interface ConfigurationRepository extends CrudRepository<ConfigParameter, String> {
}
