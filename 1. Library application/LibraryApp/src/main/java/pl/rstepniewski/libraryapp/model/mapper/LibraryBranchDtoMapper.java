package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.LibraryBranch;
import pl.rstepniewski.libraryapp.model.dto.LibraryBranchDTO;

@Component
public class LibraryBranchDtoMapper {
    public LibraryBranchDTO map(LibraryBranch libraryBranch) {
        return LibraryBranchDTO.builder()
                .id(libraryBranch.getId())
                .name(libraryBranch.getName())
                .address(libraryBranch.getAddress())
                .build();
    }

    public LibraryBranch map(LibraryBranchDTO dto) {
        return LibraryBranch.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .build();
    }
}
