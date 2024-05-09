package pl.rstepniewski.loginpage.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple_login")
public class DemoController {

    @GetMapping("/")
    public String getMain(){
        return ("<h1>main page</h1>");
    }

    @GetMapping("/allowed")
    public String getDemoAllowed(){
        return ("<h1>allowed</h1>");
    }

    @GetMapping("/restricted")
    public String getDemoRestricted(){
        return ("<h1>restricted</h1>");
    }

    @GetMapping("/admin")
    public String getDemoAdmin(){
        return ("<h1>admin only</h1>");
    }
}
