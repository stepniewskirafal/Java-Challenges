package pl.rstepniewski.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.internetshop.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
