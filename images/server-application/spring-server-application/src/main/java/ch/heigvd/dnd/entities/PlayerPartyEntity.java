package ch.heigvd.dnd.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_party")
public class PlayerPartyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;

    @ManyToOne
    private PlayerEntity player;

    @ManyToOne
    private PartyEntity party;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PartyEntity getParty() {
        return party;
    }

    public void setParty(PartyEntity party) {
        this.party = party;
    }
}
