package pl.rstepniewski.internetshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.internetshop.repositories.ConfigurationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigParameterService {
    private final ConfigurationRepository configurationRepository;

    @Value("${discount.defaultStrategy}")
    private String defaultDiscountStrategy;

    @Cacheable("configValues")
    public String getConfigValue(String configKey){
        final Optional<ConfigParameter> configValue = configurationRepository.findById(configKey);
        return configValue.isEmpty() ? defaultDiscountStrategy : configValue.get().getConfigValue();
    }

    @CacheEvict(value = {"configValues"}, allEntries = true)
    public void cacheEvict() {
    }

    @Transactional
    public ConfigParameter save(ConfigParameter parameter){
        return configurationRepository.save(parameter);
    }
}
