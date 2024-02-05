package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;
import java.util.UUID;

public class BorrowerNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public BorrowerNotFoundException(UUID id) {
        super("Borrower not found: " + id);
    }
}
