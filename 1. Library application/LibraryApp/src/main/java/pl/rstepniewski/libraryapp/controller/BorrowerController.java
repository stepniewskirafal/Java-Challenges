package pl.rstepniewski.libraryapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.model.dto.BorrowerDTO;
import pl.rstepniewski.libraryapp.services.BorrowerService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class BorrowerController {
    private final BorrowerService service;

    @GetMapping("/borrowers")
    public ResponseEntity<List<BorrowerDTO>> getBorrowers(){
        final List<BorrowerDTO> borrowerDTOS = service.getBorrowers();
        return ResponseEntity.ok(borrowerDTOS);
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<BorrowerDTO> getBorrower(@PathVariable UUID borrowerId){
        Optional<BorrowerDTO> borrowerDTO = service.getBorrowerById(borrowerId);

        return borrowerDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/borrower")
    public ResponseEntity<BorrowerDTO> addBorrower(@RequestBody @Valid BorrowerDTO borrowerDTO){
        final BorrowerDTO savedBorrowerDTO = service.save(borrowerDTO);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(savedBorrowerDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedBorrowerDTO);
    }
}
