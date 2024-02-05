package pl.rstepniewski.libraryapp.model;

import jakarta.persistence.*;
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
@Table(name = "categories")
public class Category {
    @Id
    private UUID id;
    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    @Column(unique = true)
    private String name;
    @Size(min=1, max=1000, message = "Description must be between 1 and 1000 characters")
    private String description;

    public Category() {
        this.id = UUID.randomUUID();
    }

}
