package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.rstepniewski.libraryapp.services.BookService;
import pl.rstepniewski.libraryapp.model.dto.BookDTO;
import pl.rstepniewski.libraryapp.validation.exceptions.BookPatchFormatException;
import pl.rstepniewski.libraryapp.validation.exceptions.BookPatchUnprocessableException;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("libre-app/v1.0")
public class BookController {
    private final BookService service;
    private final ObjectMapper mapper;


    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return new ResponseEntity<>( service.getAllBooks(pageNo, pageSize) , HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
     public ResponseEntity<BookDTO> getBook(@PathVariable UUID id){
        return service.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/book")
    public ResponseEntity<BookDTO> postBook(@Valid @RequestBody BookDTO bookDTO){
        BookDTO saveBook = service.saveBook(bookDTO);

        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(saveBook);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> putBook(@PathVariable UUID id,
                                     @RequestBody BookDTO bookDTO){
        Optional<BookDTO> replacedBook = service.replaceBook(id, bookDTO);
        
        return replacedBook
                .map( book -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/book/{id}")
    public ResponseEntity<?> patchBook(@PathVariable UUID id,
                                       @RequestBody JsonMergePatch patch){
        try {
            BookDTO bookDTO = service.getBookById(id).orElseThrow();
            BookDTO bookDTOPatched = applyPatch(bookDTO, patch);
            service.updateBook(bookDTOPatched);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException e) {
            throw new BookPatchFormatException();
        } catch (JsonProcessingException e) {
            throw new BookPatchUnprocessableException();
        }
        return ResponseEntity.noContent().build();
    }

    private BookDTO applyPatch(BookDTO bookDTO, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode bookNode = mapper.valueToTree(bookDTO);
        JsonNode bookNodeApplied = patch.apply(bookNode);

        return mapper.treeToValue(bookNodeApplied, BookDTO.class);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id){
        service.deleteBook(id);
        return  ResponseEntity.noContent().build();
    }

}
