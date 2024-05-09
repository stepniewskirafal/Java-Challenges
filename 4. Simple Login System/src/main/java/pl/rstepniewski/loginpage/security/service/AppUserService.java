package pl.rstepniewski.loginpage.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.rstepniewski.loginpage.security.model.AppRole;
import pl.rstepniewski.loginpage.security.model.AppUser;
import pl.rstepniewski.loginpage.security.repository.AppUserRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository userRepository;
    private final static String NOT_FOUND_MSG = "User with userName %s not found";

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final AppUser appUser = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(NOT_FOUND_MSG, email)));;
        return appUser;
    }

    public AppUser save(final String username, final String email, final String password, final Set<AppRole> authorities) {
        final AppUser appUser = new AppUser(username, email, password, authorities);
        return userRepository.save(appUser);
    }

    public AppUser save(final AppUser appUser) {
        return userRepository.save(appUser);
    }

    public Optional<AppUser> findByEmail(final String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }
}
