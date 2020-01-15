package ch.heigvd.dnd.entities;

import ch.heigvd.dnd.entities.keys.PlayerPartyKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "playerParty")
public class PlayerPartyEntity implements Serializable {
    @EmbeddedId
    private PlayerPartyKey key;

    public PlayerPartyKey getKey() {
        return key;
    }

    public void setKey(PlayerPartyKey key) {
        this.key = key;
    }
}
