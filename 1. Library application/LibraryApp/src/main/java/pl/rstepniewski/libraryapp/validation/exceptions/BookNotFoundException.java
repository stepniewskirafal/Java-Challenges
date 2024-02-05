package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;
import java.util.UUID;

public class BookNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public BookNotFoundException(){
        super("Book could not be found");
    }

    public BookNotFoundException(String isbn) {
        super("Book not found with isbn: " + isbn);
    }

    public BookNotFoundException(UUID id) {
        super("Book not found with id: " + id);
    }
}
