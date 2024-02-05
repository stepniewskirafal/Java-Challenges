package pl.rstepniewski.libraryapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.model.dto.LibraryBranchDTO;
import pl.rstepniewski.libraryapp.services.LibraryBranchService;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class LibraryBranchController {

    private final LibraryBranchService service;

    @PostMapping("/libraryBranch")
    public ResponseEntity<LibraryBranchDTO> addLibraryBranch(@RequestBody @Valid LibraryBranchDTO libraryBranchDTO) {
        final LibraryBranchDTO savedlibraryBranchDTO = service.save(libraryBranchDTO);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("id")
                .buildAndExpand(savedlibraryBranchDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedlibraryBranchDTO);
    }

    @DeleteMapping("/libraryBranch/{libraryBranchId}")
    public ResponseEntity<?> deleteLibraryBranch(@PathVariable UUID libraryBranchId) {
        service.deleteById(libraryBranchId);

        return ResponseEntity.noContent().build();
    }
}
