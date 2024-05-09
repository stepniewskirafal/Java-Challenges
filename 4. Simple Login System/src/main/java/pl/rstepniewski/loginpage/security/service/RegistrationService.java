package pl.rstepniewski.loginpage.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.loginpage.mail.service.EmailService;
import pl.rstepniewski.loginpage.security.model.ActivationToken;
import pl.rstepniewski.loginpage.security.model.AppRole;
import pl.rstepniewski.loginpage.security.model.AppUser;
import pl.rstepniewski.loginpage.security.model.dto.AppUserDto;
import pl.rstepniewski.loginpage.security.model.mapper.AppUserMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final AppRoleService appRoleService;
    private final ActivationTokenService activationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${simple-login.security.token.expiration_time_hours}")
    private Integer expirationTimeHours;

    public AppUserDto registerUser(final AppUserDto appUserDto){
        final String encodedPassword = passwordEncoder.encode(appUserDto.getPassword());
        final AppRole userRole = appRoleService.findByAuthority("ROLE_USER").get();

        Set<AppRole> authorities = new HashSet<>();
        authorities.add(userRole);

        final AppUser applicationUser = appUserService.save(appUserDto.getUsername(), appUserDto.getEmail(), encodedPassword, authorities);

        final UUID activationToken = createActivationToken(appUserDto.getEmail());
        emailService.registrationFinishedEmail(appUserDto.getEmail(), activationToken);

        return AppUserMapper.map(applicationUser);
    }

    private UUID createActivationToken(final String email) {
        final UUID randomUUID = UUID.randomUUID();
        final Timestamp expirationTime = Timestamp.valueOf(LocalDateTime.now().plusHours(expirationTimeHours));

        final ActivationToken activationToken = ActivationToken.builder()
                .tokenId(UUID.randomUUID())
                .token(randomUUID)
                .emailAddress(email)
                .expirationDate(expirationTime)
                .build();

        activationTokenService.save(activationToken);
        return randomUUID;
    }

    public Boolean verifyMail(final UUID activationToken) {
        final Optional<ActivationToken> activationTokenByToken = activationTokenService.getActiveToken(activationToken);

        if (activationTokenByToken.isPresent() && activationTokenByToken.get().getExpirationDate().before(new Timestamp(System.currentTimeMillis()))){
            return Boolean.FALSE;
        }

        final AppUser user = appUserService.findByEmail(activationTokenByToken.get().getEmailAddress()).orElseThrow(()-> new UsernameNotFoundException(""));
        user.setEnabled(Boolean.TRUE);
        appUserService.save(user);

        emailService.activationFinishedEmail(user.getEmail());

        return Boolean.TRUE;
    }


}
