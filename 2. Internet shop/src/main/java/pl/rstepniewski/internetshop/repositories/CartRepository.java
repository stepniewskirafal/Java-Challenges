package pl.rstepniewski.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.internetshop.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
