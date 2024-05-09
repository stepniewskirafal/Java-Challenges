package pl.rstepniewski.loginpage.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.rstepniewski.loginpage.security.model.ActivationToken;

import java.util.Optional;
import java.util.UUID;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, UUID> {
    @Query("SELECT a " +
            "FROM ActivationToken a " +
            "WHERE a.token = :token " +
            "AND a.expirationDate >= CURRENT_TIMESTAMP " +
            "ORDER BY a.expirationDate ASC")
    Optional<ActivationToken> findFirstActiveTokenByToken(@Param("token") UUID token);
}

