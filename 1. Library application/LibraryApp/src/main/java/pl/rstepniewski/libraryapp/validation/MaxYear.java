package pl.rstepniewski.libraryapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = MaxYearValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface MaxYear {

    String message() default "Year can not be future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}