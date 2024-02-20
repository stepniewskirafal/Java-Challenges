package pl.rstepniewski.internetshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Cart {
    @Id
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    private BigDecimal sumPrice;

    public Cart() {
        this.id = UUID.randomUUID();
    }
}
