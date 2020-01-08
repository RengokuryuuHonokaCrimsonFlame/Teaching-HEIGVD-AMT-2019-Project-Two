package ch.heigvd.dnd.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "utilisateur")
public class UtilisateurEntity implements Serializable {
    @Id
    @Column(name="email", unique=true)
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="administrator")
    private boolean administrator;
    @Column(name="blocked")
    private boolean blocked;


    public String getEmail(){ return email;}

    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
