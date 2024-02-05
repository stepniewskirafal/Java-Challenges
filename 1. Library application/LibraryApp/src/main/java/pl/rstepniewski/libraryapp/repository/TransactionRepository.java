package pl.rstepniewski.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rstepniewski.libraryapp.model.Transaction;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
