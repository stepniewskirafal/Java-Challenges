package pl.rstepniewski.libraryapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction{
    @Id
    private UUID id;
    @NotNull
    private Date transactionDate;
    @NotNull
    private boolean isReturn;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Book> books;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Borrower borrower;

    public Transaction() {
        this.id = UUID.randomUUID();
    }
}
