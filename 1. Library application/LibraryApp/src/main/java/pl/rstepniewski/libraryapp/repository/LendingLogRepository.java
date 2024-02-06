package pl.rstepniewski.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rstepniewski.libraryapp.model.LendingLog;

import java.util.UUID;

public interface LendingLogRepository extends JpaRepository<LendingLog, UUID> {
}
