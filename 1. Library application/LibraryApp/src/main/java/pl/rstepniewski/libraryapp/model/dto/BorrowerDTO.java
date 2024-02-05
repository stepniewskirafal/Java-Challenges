package pl.rstepniewski.libraryapp.model.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BorrowerDTO {
    @Id
    private UUID id;

    private long pesel;

    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    private String name;

    @NotNull
    @Size(min=1, max=100, message = "Last name length must be between 1 and 100 characters")
    private String lastName;
}
