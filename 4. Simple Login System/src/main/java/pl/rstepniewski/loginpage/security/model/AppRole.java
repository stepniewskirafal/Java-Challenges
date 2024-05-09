package pl.rstepniewski.loginpage.security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.UUID;

@Entity
@Table(name="roles")
public class AppRole implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="role_id")
    private UUID roleId;

    @Size(min = 3, max = 20)
    private String authority;

    public AppRole(){
        super();
    }

    public AppRole(final String authority){
        super();
        this.roleId = UUID.randomUUID();
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.authority;
    }

    public void setAuthority(final String authority){
        this.authority = authority;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(final UUID roleId) {
        this.roleId = roleId;
    }
}