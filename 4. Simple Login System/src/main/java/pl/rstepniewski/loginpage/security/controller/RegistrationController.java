package pl.rstepniewski.loginpage.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;
import pl.rstepniewski.loginpage.security.service.AppUserService;
import pl.rstepniewski.loginpage.security.service.RegistrationService;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/simple_login")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final AppUserService appUserService;

    @PostMapping("/register")
    public String registerForm(@Valid @ModelAttribute("user") AppUserDto appUserDto,
                               BindingResult bindingResult,
                               Model model) {
        if (appUserService.existsByEmail(appUserDto.getEmail())) {
            model.addAttribute("error", "Email address is already in use");
            return "auth/registration-form";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", appUserDto);
            return "auth/registration-form";
        }
        registrationService.registerUser(appUserDto);
        return "redirect:/simple_login/register-confirmation";
    }

    @GetMapping("/register")
    public String registrationForm(final Model model) {
        AppUserDto user = new AppUserDto();
        model.addAttribute("user", user);
        return "auth/registration-form";
    }

    @GetMapping("/register-confirmation")
    public String registrationConfirmation() {
        return "auth/registration-confirmation";
    }

    @GetMapping("/verifyMail/{activationToken}")
    public String verifyMail(final @PathVariable UUID activationToken) {
        final Boolean accepted = registrationService.verifyMail(activationToken);

        return accepted ? "auth/verifyMail-confirmation" : "auth/verifyMail-deny";
    }
}
