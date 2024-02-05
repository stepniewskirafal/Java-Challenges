package pl.rstepniewski.libraryapp.validation.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorObject errorObject = new ErrorObject(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                new Date()
        );

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBookNotFoundException(BookNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("ConstraintViolation: " + e.getMessage());
    }

    @ExceptionHandler(LibraryBranchAlreadyExistsException.class)
    public ResponseEntity<String> handleLibraryBranchAlreadyExistsException(LibraryBranchAlreadyExistsException e) {
        return ResponseEntity.badRequest().body("exception: " + e.getMessage());
    }

    @ExceptionHandler(CategoryBranchAlreadyExistsException.class)
    public ResponseEntity<String> handleCategoryBranchAlreadyExistsException(CategoryBranchAlreadyExistsException e) {
        return ResponseEntity.badRequest().body("exception: " + e.getMessage());
    }

    @ExceptionHandler(BorrowerNotFoundException.class)
    public ResponseEntity<String> handleBorrowerNotFoundException(BorrowerNotFoundException e) {
        return ResponseEntity.badRequest().body("exception: " + e.getMessage());
    }

    @ExceptionHandler(BorrowerAlreadyExistsException.class)
    public ResponseEntity<String> handleBorrowerAlreadyExistsException(BorrowerAlreadyExistsException e) {
        return ResponseEntity.badRequest().body("exception: " + e.getMessage());
    }

}
