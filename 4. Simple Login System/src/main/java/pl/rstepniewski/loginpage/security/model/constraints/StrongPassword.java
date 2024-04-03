package pl.rstepniewski.loginpage.security.model.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface StrongPassword {
    String message() default "Password must contain at least one digit, one special character, and have a minimum length of 8 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}