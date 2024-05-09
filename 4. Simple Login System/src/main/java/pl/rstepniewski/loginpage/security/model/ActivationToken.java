package pl.rstepniewski.loginpage.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="activation_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivationToken {
    @Id
    @Column(name = "token_id", nullable = false)
    private UUID tokenId;

    private UUID token;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    public UUID getTokenId() {
        return tokenId;
    }

}
