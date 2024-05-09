package pl.rstepniewski.loginpage.mail;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailProperties {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String senderEmailAddress;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.starttls}")
    private Boolean starttls;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean auth;
}
