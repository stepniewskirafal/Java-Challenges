package pl.rstepniewski.libraryapp.model.dto;

import jakarta.persistence.Column;
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
public class CategoryDTO {
    @Id
    private UUID id;
    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    @Column(unique = true)
    private String name;
    @Size(min=1, max=1000, message = "Description must be between 1 and 1000 characters")
    private String description;
}
