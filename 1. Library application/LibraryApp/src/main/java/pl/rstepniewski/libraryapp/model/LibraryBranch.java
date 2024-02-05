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
@Table(name = "library_branches", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "address"}, name = "unique_library_branch_name_address")
})
public class LibraryBranch {
    @Id
    private UUID id;

    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    private String name;

    @NotNull
    @Size(min=1, max=100, message = "Name length must be between 1 and 100 characters")
    private String address;

    public LibraryBranch() {
        this.id = UUID.randomUUID();
    }

}
