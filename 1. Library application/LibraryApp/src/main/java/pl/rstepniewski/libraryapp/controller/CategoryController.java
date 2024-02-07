package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.model.dto.CategoryDTO;
import pl.rstepniewski.libraryapp.services.CategoryService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class CategoryController {

    private final CategoryService service;
    private final ObjectMapper mapper;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        final List<CategoryDTO> categoryDTOS = service.findAll();
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable UUID categoryId){
        Optional<CategoryDTO> categoryDTO = service.findById(categoryId);

        return categoryDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO){

        final CategoryDTO savedcategoryDTO = service.save(categoryDTO);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(savedcategoryDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedcategoryDTO);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable @Valid UUID categoryId){
        service.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
