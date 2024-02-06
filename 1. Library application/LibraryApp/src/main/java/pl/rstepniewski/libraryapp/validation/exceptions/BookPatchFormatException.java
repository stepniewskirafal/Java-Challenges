package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;

public class BookPatchFormatException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public BookPatchFormatException(){
        super("Patch couldn't be processed");
    }
}
