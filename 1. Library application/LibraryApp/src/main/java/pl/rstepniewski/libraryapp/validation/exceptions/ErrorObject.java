package pl.rstepniewski.libraryapp.validation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
