package pl.rstepniewski.loginpage.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.loginpage.security.model.AppRole;
import pl.rstepniewski.loginpage.security.model.AppUser;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;
import pl.rstepniewski.loginpage.security.model.mapper.AppUserMapper;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final AppRoleService appRoleService;
    private final PasswordEncoder passwordEncoder;

    public AppUserDto registerUser(AppUserDto dto){
        final String encodedPassword = passwordEncoder.encode(dto.getPassword());
        AppRole userRole = appRoleService.findByAuthority("USER").get();

        Set<AppRole> authorities = new HashSet<>();
        authorities.add(userRole);

        final AppUser applicationUser = appUserService.save(dto.getUsername(), dto.getEmail(), encodedPassword, authorities);

        return AppUserMapper.map(applicationUser);
    }
}
