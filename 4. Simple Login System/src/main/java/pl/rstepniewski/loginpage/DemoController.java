package pl.rstepniewski.loginpage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login_page")
public class DemoController {

    @GetMapping("/allowed")
    public String getDemoAllowed(){
        return ("<h1>allowed</h1>");
    }

    @GetMapping("/restricted")
    public String getDemoRestricted(){
        return ("<h1>restricted</h1>");
    }
}
