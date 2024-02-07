package pl.rstepniewski.libraryapp.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import pl.rstepniewski.libraryapp.validation.MaxYear;

import java.util.UUID;

@Getter
@Setter
@Builder
public class BookDTO {
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

    private CategoryDTO category;

    private LibraryBranchDTO libraryBranch; // zmieniłem na potrzeby optymalizacji tak żeby można było ododawać książki bez potrzeby przebudowywania całego obiektu library branch

}
