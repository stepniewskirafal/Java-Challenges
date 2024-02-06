package pl.rstepniewski.libraryapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;
import pl.rstepniewski.libraryapp.services.LendingLogService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class LendingLogController {

    private final LendingLogService service;

    @PostMapping("/lending-log")
    public ResponseEntity<LendingLogDTO> addLog(@RequestBody @Valid LendingLogDTO lendingLogDTO){
        final LendingLogDTO savedlendingLogDTO = service.save(lendingLogDTO);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("id")
                .buildAndExpand(savedlendingLogDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedlendingLogDTO);
    }
}
