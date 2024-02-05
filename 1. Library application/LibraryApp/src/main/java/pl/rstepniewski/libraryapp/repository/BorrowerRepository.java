package pl.rstepniewski.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rstepniewski.libraryapp.model.Borrower;

import java.util.Optional;
import java.util.UUID;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
    Optional<Borrower> findByPesel(long pesel);
}
