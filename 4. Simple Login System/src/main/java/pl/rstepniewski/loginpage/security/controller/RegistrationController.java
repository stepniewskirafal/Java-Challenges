package pl.rstepniewski.loginpage.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;
import pl.rstepniewski.loginpage.security.service.RegistrationService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login_page")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String registerForm(AppUserDto dto) {
        registrationService.registerUser(dto);
        return "redirect:/confirmation";
    }

    @GetMapping("/register")
    public String registrationForm(final Model model) {
        AppUserDto user = new AppUserDto();
        model.addAttribute("user", user);
        return "auth/registration-form";
    }

    @GetMapping("/confirmation")
    public String registrationConfirmation() {
        return "auth/registration-confirmation";
    }
}
