package pl.rstepniewski.loginpage.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.rstepniewski.loginpage.security.model.constraints.StrongPassword;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class AppUser implements UserDetails{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_id")
    private UUID userId;

    @Column(name="user_name", unique=true, nullable = false)
    @NotBlank(message = "Nickname must not be blank")
    @Size(min = 3, max = 30)
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email must not be blank")
    @Size(max = 40)
    @Email(message = "Invalid email address")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @StrongPassword
    @JsonIgnore
    private String password;

    private Boolean enabled;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<AppRole> authorities;

    public AppUser() {
        super();
        authorities = new HashSet<>();
    }

    public AppUser(final String username, final String email, final String password, final Set<AppRole> authorities) {
        super();
        this.userId = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = false;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setAuthorities(final Set<AppRole> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    /* If you want account locking capabilities create variables and ways to set them for the methods below */
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}