package pl.rstepniewski.libraryapp.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LendingLogDTO {
    @Id
    private UUID id;

    @NotNull
    private Date transactionDate;

    @NotNull
    private boolean isReturn;

    private Set<BookDTO> books;

    private BorrowerDTO borrower;
}
