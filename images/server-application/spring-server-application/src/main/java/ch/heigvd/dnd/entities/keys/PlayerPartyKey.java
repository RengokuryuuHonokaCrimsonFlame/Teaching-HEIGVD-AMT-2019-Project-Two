package ch.heigvd.dnd.entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlayerPartyKey implements Serializable {
    @Column(name = "fkPlayer", nullable = false)
    private String fkPlayer;

    @Column(name = "fkParty", nullable = false)
    private String fkParty;

    public String getFkPlayer() {
        return fkPlayer;
    }

    public void setFkPlayer(String fkPlayer) {
        this.fkPlayer = fkPlayer;
    }

    public String getFkParty() {
        return fkParty;
    }

    public void setFkParty(String fkParty) {
        this.fkParty = fkParty;
    }

}
