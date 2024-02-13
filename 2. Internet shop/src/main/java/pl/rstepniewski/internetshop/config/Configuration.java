package pl.rstepniewski.internetshop.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "configurations")
public class Configuration {
    @Id
    private String configKey;
    private String configValue;
}
