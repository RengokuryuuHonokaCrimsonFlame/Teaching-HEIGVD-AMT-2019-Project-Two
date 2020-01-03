package ch.heigvd.dnd.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PlayerEntity implements Serializable {
    @Id
    private String id;

    private String password;
    private int strength;
    private int dexterity;
    private int constitution;
    private int  intelligence;
    private int wisdom;
    private int charisma;
    private String race;
    private String classe;

    public String getId(){ return id;}

    public void setId(String id){this.id = id;}

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
}
