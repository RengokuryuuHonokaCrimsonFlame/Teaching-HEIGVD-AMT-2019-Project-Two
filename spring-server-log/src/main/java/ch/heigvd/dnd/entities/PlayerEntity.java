package ch.heigvd.dnd.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "player")
public class PlayerEntity implements Serializable {
    @Id
    @Column(name="email", unique=true)
    private String email;

    @Column(name="pseudo")
    private String pseudo;
    @Column(name="password")
    private String password;
    @Column(name="strength")
    private int strength;
    @Column(name="dexterity")
    private int dexterity;
    @Column(name="constitution")
    private int constitution;
    @Column(name="intelligence")
    private int  intelligence;
    @Column(name="wisdom")
    private int wisdom;
    @Column(name="charisma")
    private int charisma;
    @Column(name="race")
    private String race;
    @Column(name="classe")
    private String classe;
    @Column(name="administrator")
    private boolean administrator;
    @Column(name="blocked")
    private boolean blocked;


    public String getEmail(){ return email;}

    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public int getStrength(){return strength;}

    public void setStrength(int strength){this.strength = strength;}

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

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
