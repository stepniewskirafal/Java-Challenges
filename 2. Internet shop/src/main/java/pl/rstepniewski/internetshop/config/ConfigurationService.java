package pl.rstepniewski.internetshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Value("${discount.defaultStrategy}")
    private String defaultDiscountStrategy;

    @Cacheable("configValues")
    public String getConfigValue(String configKey){
        final Optional<Configuration> configValue = configurationRepository.findById(configKey);
        return configValue.isEmpty() ? defaultDiscountStrategy : configValue.get().getConfigValue();
    }

}
