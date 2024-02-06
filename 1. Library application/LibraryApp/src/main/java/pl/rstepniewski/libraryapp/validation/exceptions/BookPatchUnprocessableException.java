package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;

public class BookPatchUnprocessableException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public BookPatchUnprocessableException(){
        super("Patch couldn't be processed");
    }
}
