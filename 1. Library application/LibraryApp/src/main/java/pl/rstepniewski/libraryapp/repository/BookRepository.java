package pl.rstepniewski.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.libraryapp.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
}
