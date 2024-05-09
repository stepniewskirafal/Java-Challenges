package pl.rstepniewski.loginpage.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rstepniewski.loginpage.security.model.ActivationToken;
import pl.rstepniewski.loginpage.security.repository.ActivationTokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivationTokenService {

    private final ActivationTokenRepository activationTokenRepository;
    public ActivationToken save(final ActivationToken activationToken) {
        return activationTokenRepository.save(activationToken);
    }

    public Optional<ActivationToken> getActiveToken(final UUID token) {
        return activationTokenRepository.findFirstActiveTokenByToken(token);
    }
}
