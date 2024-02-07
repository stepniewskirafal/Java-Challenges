package pl.rstepniewski.libraryapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import pl.rstepniewski.libraryapp.validation.MaxYear;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    private UUID id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title length must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 100, message = "Author length must be between 1 and 100 characters")
    private String author;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    @Min(value = 1900, message = "The year must be 1900 or later")
    @MaxYear(message = "Year can not be future")
    private int yearPublished;

    @ISBN
    @Column(unique = true)
    private String isbn;

    @NotNull
    private boolean isAvailable;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private LibraryBranch libraryBranch;

    public Book() {
        this.id = UUID.randomUUID();
    }

}