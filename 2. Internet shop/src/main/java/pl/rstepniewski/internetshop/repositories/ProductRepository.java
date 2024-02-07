package pl.rstepniewski.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.internetshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
