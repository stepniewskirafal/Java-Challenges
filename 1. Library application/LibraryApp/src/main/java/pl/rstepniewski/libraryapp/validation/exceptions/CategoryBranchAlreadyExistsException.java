package pl.rstepniewski.libraryapp.validation.exceptions;

import java.io.Serial;

public class CategoryBranchAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public CategoryBranchAlreadyExistsException(){
        super("Category already exists");
    }
    public CategoryBranchAlreadyExistsException(String duplicat){
        super("Category already exists, duplicated data: "+duplicat);
    }

}
