package pl.rstepniewski.loginpage.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;

@Controller
@RequestMapping("/simple_login")
class LoginController {

    @GetMapping("/login")
    public String loginForm(final Model model) {
        AppUserDto user = new AppUserDto();
        model.addAttribute("user", user);
        return "auth/login-form";
    }

    @GetMapping("/logout-success")
    public String logoutForm() {
        return "auth/logout-form";
    }
}