package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;

public class BorrowerAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public BorrowerAlreadyExistsException(){
        super("Borrower already exists");
    }

    public BorrowerAlreadyExistsException(long pesel){
        super("Borrower already exists. Borrower's pesel: "+pesel);
    }
}
