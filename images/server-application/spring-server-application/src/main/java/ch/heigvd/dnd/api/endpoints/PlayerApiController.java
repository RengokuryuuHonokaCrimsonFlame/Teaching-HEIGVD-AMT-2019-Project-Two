package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.PlayerApi;
import ch.heigvd.dnd.api.dto.Party;
import ch.heigvd.dnd.api.dto.Player;
import ch.heigvd.dnd.api.dto.Playerpage;
import ch.heigvd.dnd.api.dto.Mypage;
import ch.heigvd.dnd.entities.PartyEntity;
import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.entities.PlayerPartyEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.PartyRepository;
import ch.heigvd.dnd.repositories.PlayerPartyRepository;
import ch.heigvd.dnd.repositories.PlayerRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-11T10:11:52.805Z")

@Controller
public class PlayerApiController implements PlayerApi {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PlayerPartyRepository playerPartyRepository;

    public ResponseEntity<Object> createParty(@ApiParam(value = "the new party" ,required=true )  @Valid @RequestBody Party party, @ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<PlayerEntity> pe = playerRepository.findById(userId);
        if(new JwttokenLogic().isTokenExpired(xDndToken) || !pe.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        partyRepository.save(PartyApiController.getPartyEntityFromParty(party));
        return ResponseEntity.status(HttpStatus.CREATED).body(party);
    }

    public ResponseEntity<Object> getplayer(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@Min(0)@ApiParam(value = "The number of the page") @Valid @RequestParam(value = "pagination", required = false) Integer pagination) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        if(new JwttokenLogic().isTokenExpired(xDndToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<PlayerEntity> player = playerRepository.findById(userId);
        if(player.isPresent()) {
            Playerpage pp = new Playerpage();
            pp.setPlayer(getPlayerFromPlayerEntity(player.get()));
            pp.setMypage(getMyPage(pagination, userId));
            pp.setParties(getPlayerParties(pagination, userId));
            return ResponseEntity.status(HttpStatus.OK).body(pp);
        }else{
            PlayerEntity pe = new PlayerEntity();
            pe.setEmail(userId);
            pe.setCharisma(6);
            pe.setClasse("Undifined");
            pe.setConstitution(6);
            pe.setDexterity(6);
            pe.setIntelligence(6);
            pe.setPseudo("Undifined");
            pe.setRace("Undifined");
            pe.setStrength(6);
            pe.setWisdom(6);
            playerRepository.save(pe);
            Playerpage pp = new Playerpage();
            pp.setPlayer(getPlayerFromPlayerEntity(pe));
            pp.setMypage(getMyPage(0, userId));
            pp.setParties(getPlayerParties(0, userId));
            return ResponseEntity.status(HttpStatus.CREATED).body(pp);
        }
    }

    public ResponseEntity<Void> updateplayer(@ApiParam(value = "the player" ,required=true )  @Valid @RequestBody Player player, @ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken) {
        if(!new JwttokenLogic().validateToken(xDndToken, player.getEmail())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PlayerEntity pe = getPlayerEntityFromPlayer(player);
        playerRepository.save(pe);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private List<Party> getPlayerParties(Integer page, String email){
        Pageable myPage = PageRequest.of(page, 60, Sort.by("party_id"));
        Page<PlayerPartyEntity> relations = playerPartyRepository.findByPlayerEmail(email, myPage);
        List<Party> parties = new LinkedList<>();
        for(PlayerPartyEntity ppe : relations){
            Optional<PartyEntity> partie = partyRepository.findById(ppe.getParty().getId());
            parties.add(PartyApiController.getPartyFromPartyEntity(partie.get()));
        }
        return parties;
    }

    private Mypage getMyPage(Integer page, String email){
        Mypage p = new Mypage();
        p.setPagination(page);
        if(page <= 0){
            p.setPrevious("-");
        }else{
            p.setPrevious("www.heigvd-dnd.ch/player?page=" + (page - 1));
        }
        p.setNbEntries(playerPartyRepository.countByPlayerEmail(email));
        if(page >= (p.getNbEntries()/60)){
            p.setNext("-");
        }else{
            p.setNext("www.heigvd-dnd.ch/player?page=" + (page + 1));
        }
        return p;
    }

    static Player getPlayerFromPlayerEntity(PlayerEntity pe){
        Player p = new Player();
        p.setEmail(pe.getEmail());
        p.setPseudo(pe.getPseudo());
        p.setStrength(pe.getStrength());
        p.setIntelligence(pe.getIntelligence());
        p.setCharisma(pe.getCharisma());
        p.setConstitution(pe.getConstitution());
        p.setDexterity(pe.getDexterity());
        p.setWisdom(pe.getWisdom());
        p.setClasse(pe.getClasse());
        p.setRace(pe.getRace());
        return p;
    }

    static PlayerEntity getPlayerEntityFromPlayer(Player p){
        PlayerEntity pe = new PlayerEntity();
        pe.setEmail(p.getEmail());
        pe.setPseudo(p.getPseudo());
        pe.setStrength(p.getStrength());
        pe.setIntelligence(p.getIntelligence());
        pe.setCharisma(p.getCharisma());
        pe.setConstitution(p.getConstitution());
        pe.setDexterity(p.getDexterity());
        pe.setWisdom(p.getWisdom());
        pe.setClasse(p.getClasse());
        pe.setRace(p.getRace());
        return pe;
    }
}
