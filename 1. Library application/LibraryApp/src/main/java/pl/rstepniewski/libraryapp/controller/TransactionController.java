package pl.rstepniewski.libraryapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.model.dto.TransactionDTO;
import pl.rstepniewski.libraryapp.services.TransactionService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> addTransaction(@RequestBody @Valid TransactionDTO transactionDTO){
        final TransactionDTO savedTransactionDTO = service.save(transactionDTO);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("id")
                .buildAndExpand(savedTransactionDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedTransactionDTO);
    }
}
