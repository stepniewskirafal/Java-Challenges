package pl.rstepniewski.loginpage.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import pl.rstepniewski.loginpage.mail.EmailConnector;
import pl.rstepniewski.loginpage.mail.MailProperties;
import pl.rstepniewski.loginpage.mail.model.EmailBody;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConnector emailConnector;
    private final MailProperties mailProperties;

    @Retryable(value = {HttpClientErrorException.class, HttpServerErrorException.class, HttpStatusCodeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 200))
    public void sendSimpleMessage(EmailBody emailBody) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(mailProperties.getSenderEmailAddress());
        smm.setTo(emailBody.getReceiverEmail() );
        smm.setSubject(emailBody.getSubject() );
        smm.setText(emailBody.getMessage() );
        emailConnector.sender.send(smm);
    }

    public void registrationFinishedEmail(String email, UUID activationToken) {
        final EmailBody emailBody = EmailBody.builder()
                .receiverEmail(email)
                .subject("Rejestracja zakończona powodzeniem")
                .message("Potwierdź proces rejestracji http://localhost:8080/simple_login/verifyMail/"+ activationToken)
                .build();

        sendSimpleMessage(emailBody);
    }

    public void activationFinishedEmail(String email) {
        final EmailBody emailBody = EmailBody.builder()
                .receiverEmail(email)
                .subject("Aktywacja adresu email zakończona powodzeniem")
                .message("Możesz się teraz zalogować ")
                .build();

        sendSimpleMessage(emailBody);
    }
}

