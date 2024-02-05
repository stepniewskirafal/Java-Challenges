package pl.rstepniewski.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.libraryapp.model.LibraryBranch;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, UUID> {

    Optional<LibraryBranch> findByNameEqualsIgnoreCase(String name);

    Optional<LibraryBranch> findByAddressEqualsIgnoreCase(String address);
}
