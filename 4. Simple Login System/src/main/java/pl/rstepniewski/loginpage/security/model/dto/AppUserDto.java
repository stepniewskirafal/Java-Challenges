package pl.rstepniewski.loginpage.security.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.rstepniewski.loginpage.security.model.constraints.StrongPassword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {
    @NotBlank(message = "Nickname must not be blank")
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @StrongPassword
    @JsonIgnore
    private String password;
}