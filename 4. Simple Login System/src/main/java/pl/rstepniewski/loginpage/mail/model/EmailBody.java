package pl.rstepniewski.loginpage.mail.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class EmailBody {
    @Id
    @Column(name = "email_id", nullable = false)
    private UUID emailId;

    private String senderEmail;
    private String receiverEmail;
    private String subject;
    private String message;

    public EmailBody() {
        this.emailId = UUID.randomUUID();
    }
}
