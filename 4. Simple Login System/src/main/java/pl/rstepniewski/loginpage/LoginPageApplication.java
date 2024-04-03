package pl.rstepniewski.loginpage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.rstepniewski.loginpage.security.model.AppRole;
import pl.rstepniewski.loginpage.security.model.AppUser;
import pl.rstepniewski.loginpage.security.repository.AppRoleRepository;
import pl.rstepniewski.loginpage.security.repository.AppUserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class LoginPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginPageApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppRoleRepository roleRepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
            final AppRole adminRole = roleRepository.save(new AppRole("ADMIN"));
            final AppRole userRole = roleRepository.save(new AppRole("USER"));
            final Set<AppRole> roles = new HashSet<>();
            roles.add(adminRole);
            final AppUser applicationUserAdmin = new AppUser("Admin", "email@test.com", passwordEncoder.encode("QAZ2ws@x"), roles);
            //final ApplicationUser applicationUserAdmin = new ApplicationUser("Admin", passwordEncoder.encode("QAZ2ws@x"), roles);
            userRepository.save(applicationUserAdmin);
        };
    }
}
