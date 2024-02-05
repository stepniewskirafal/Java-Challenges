package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;

public class LibraryBranchAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public LibraryBranchAlreadyExistsException(){
        super("Library branch already exists");
    }
    public LibraryBranchAlreadyExistsException(String duplicat){
        super("Library branch already exists, duplicated data: "+duplicat);
    }

}
