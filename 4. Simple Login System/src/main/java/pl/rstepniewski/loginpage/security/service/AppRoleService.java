package pl.rstepniewski.loginpage.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.loginpage.security.model.AppRole;
import pl.rstepniewski.loginpage.security.repository.AppRoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppRoleService {

    private final AppRoleRepository roleRepository;

    public Optional<AppRole> findByAuthority(final String authority) {
        return roleRepository.findByAuthority(authority);
    }
}
