package pl.rstepniewski.libraryapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "borrowers")
public class Borrower {

    @Id
    private UUID id;

    @Column(unique = true)
    @Digits(integer = 11, fraction = 0, message = "Pesel must contain 11 digits")
    private long pesel;

    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    private String name;

    @NotNull
    @Size(min=1, max=100, message = "Last name length must be between 1 and 100 characters")
    private String lastName;

    public Borrower() {
        this.id = UUID.randomUUID();
    }
}
