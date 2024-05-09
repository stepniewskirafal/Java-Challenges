package pl.rstepniewski.loginpage.mail;

import jakarta.annotation.PostConstruct;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailConnector {
    private final MailProperties mailProperties;

    public JavaMailSender sender;

    public EmailConnector(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
        this.sender = new JavaMailSenderImpl();
    }

    @PostConstruct
    public void init() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getSenderEmailAddress());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", mailProperties.getStarttls());
        props.put("mail.smtp.auth", mailProperties.getAuth());

        this.sender = mailSender;
    }
}
