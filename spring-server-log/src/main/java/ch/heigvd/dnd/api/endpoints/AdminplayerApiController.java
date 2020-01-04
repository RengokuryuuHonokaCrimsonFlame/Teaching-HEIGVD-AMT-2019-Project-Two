package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.AdminplayerApi;
import ch.heigvd.dnd.api.dto.Player;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
import ch.heigvd.dnd.entities.JwttokenEntity;
import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.repositories.JwttokenRepository;
import ch.heigvd.dnd.repositories.PlayerRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class AdminplayerApiController implements AdminplayerApi {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    JwttokenRepository jwttokenRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    public ResponseEntity<List<Player>> adminplayer(@ApiParam(value = "header that contain a JwtToken" ,required=true)
                              @RequestHeader(value="x-dnd-token", required=true) String xDndToken,
                              @ApiParam(value = "the page to see" ,required=true )  @Valid @RequestParam Integer page) {
        String userId = new JwttokenEntity().getUsernameFromToken(xDndToken);
        Optional<PlayerEntity> administrator = playerRepository.findById(userId);
        Optional<JwttokenEntity> token = jwttokenRepository.findById(xDndToken);
        if(token.isPresent() && administrator.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(administrator.get().getEmail());
            if (administrator.get().isAdministrator() && !administrator.get().isBlocked()
                    && new JwttokenEntity().validateToken(xDndToken, userDetails)) {
                return getPlayers(page);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<List<Player>> manageplayer(@ApiParam(value = "header that contain a JwtToken" ,required=true)
                        @RequestHeader(value="x-dnd-token", required=true) String xDndToken,
                        @ApiParam(value = "the user to change right" ,required=true )  @Valid @RequestBody String id) {
        String userId = new JwttokenEntity().getUsernameFromToken(xDndToken);
        Optional<PlayerEntity> administrator = playerRepository.findById(userId);
        Optional<JwttokenEntity> token = jwttokenRepository.findById(xDndToken);
        if(token.isPresent() &&administrator.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(administrator.get().getEmail());
            if (administrator.get().isAdministrator() && !administrator.get().isBlocked()
                && new JwttokenEntity().validateToken(xDndToken, userDetails)) {
                Optional<PlayerEntity> updatePlayer = playerRepository.findById(id);
                if(updatePlayer.isPresent()){
                    PlayerEntity playerToUpdate = updatePlayer.get();
                    playerToUpdate.setBlocked(!playerToUpdate.isBlocked());
                    playerRepository.save(playerToUpdate);
                }
                return getPlayers(0);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private ResponseEntity<List<Player>> getPlayers(int page){
        Pageable myPage = PageRequest.of(page, 60, Sort.by("email"));
        Page<PlayerEntity> players = playerRepository.findAll(myPage);
        List<Player> result = new LinkedList();
        for(PlayerEntity p : players){
            result.add(playerEntityToPlayer(p));
        }
        return new ResponseEntity<List<Player>>(result, HttpStatus.OK);
    }

    private Player playerEntityToPlayer(PlayerEntity pe){
        Player p = new Player();
        p.setEmail(pe.getEmail());
        p.setPseudo(pe.getPseudo());
        p.setPassword(pe.getPassword());
        p.setStrength(pe.getStrength());
        p.setDexterity(pe.getDexterity());
        p.setConstitution(pe.getConstitution());
        p.setIntelligence(pe.getIntelligence());
        p.setWisdom(pe.getWisdom());
        p.setCharisma(pe.getCharisma());
        p.setRace(pe.getRace());
        p.setClasse(pe.getClasse());
        p.setAdministrator(pe.isAdministrator());
        p.setBlocked(pe.isBlocked());
        return p;
    }
}
