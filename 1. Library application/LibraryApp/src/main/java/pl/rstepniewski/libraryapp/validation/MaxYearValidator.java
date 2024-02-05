package pl.rstepniewski.libraryapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MaxYearValidator implements ConstraintValidator<MaxYear, Integer> {

@Override
public void initialize(MaxYear constraintAnnotation) {
        }

@Override
public boolean isValid(Integer year, ConstraintValidatorContext context) {
    if (year == null) {
        return false;
    }

    int currentYear = LocalDate.now().getYear();
    return year <= currentYear;
}
}