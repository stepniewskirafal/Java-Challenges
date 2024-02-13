package pl.rstepniewski.internetshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private UUID id;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Product> products;

    private double sumPrice;

    public Cart(UUID id) {
        this.id = UUID.randomUUID();
    }
}
