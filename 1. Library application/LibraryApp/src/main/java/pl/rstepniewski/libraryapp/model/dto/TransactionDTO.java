package pl.rstepniewski.libraryapp.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.rstepniewski.libraryapp.model.Book;
import pl.rstepniewski.libraryapp.model.Borrower;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TransactionDTO {
    @Id
    private UUID id;

    @NotNull
    private Date transactionDate;

    @NotNull
    private boolean isReturn;

    private Set<Book> books;

    private Borrower borrower;
}
