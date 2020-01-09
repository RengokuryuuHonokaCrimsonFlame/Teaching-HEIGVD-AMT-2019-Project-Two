package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.AdminplayerApi;
import ch.heigvd.dnd.api.dto.Simpleuser;
import ch.heigvd.dnd.api.dto.Utilisateur;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
import ch.heigvd.dnd.entities.UtilisateurEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.UtilisateurRepository;
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
import javax.validation.constraints.Min;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class AdminplayerApiController implements AdminplayerApi {

    @Autowired
    UtilisateurRepository playerRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    public ResponseEntity<List<Utilisateur>> adminplayer(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@Min(0)@ApiParam(value = "The number of the page") @Valid @RequestParam(value = "pagination", required = false) Integer pagination) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<UtilisateurEntity> administrator = playerRepository.findById(userId);
        if(administrator.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(administrator.get().getEmail());
            if (administrator.get().isAdministrator() && !administrator.get().isBlocked()
                    && new JwttokenLogic().validateToken(xDndToken, userDetails)) {
                return getPlayers(pagination);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    public ResponseEntity<List<Utilisateur>> manageplayer(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the player to lock/unlock" ,required=true )  @Valid @RequestBody Simpleuser simpleuser) {
       String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<UtilisateurEntity> administrator = playerRepository.findById(userId);
        if(administrator.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(administrator.get().getEmail());
            if (administrator.get().isAdministrator() && !administrator.get().isBlocked()
                && new JwttokenLogic().validateToken(xDndToken, userDetails)) {
                Optional<UtilisateurEntity> updatePlayer = playerRepository.findById(simpleuser.getUserid());
                if(updatePlayer.isPresent()){
                    UtilisateurEntity playerToUpdate = updatePlayer.get();
                    playerToUpdate.setBlocked(!playerToUpdate.isBlocked());
                    playerRepository.save(playerToUpdate);
                }
                return getPlayers(0);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private ResponseEntity<List<Utilisateur>> getPlayers(int page){
        Pageable myPage = PageRequest.of(page, 60, Sort.by("email"));
        Page<UtilisateurEntity> players = playerRepository.findAll(myPage);
        List<Utilisateur> result = new LinkedList();
        for(UtilisateurEntity p : players){
            result.add(playerEntityToPlayer(p));
        }
        return new ResponseEntity<List<Utilisateur>>(result, HttpStatus.OK);
    }

    private Utilisateur playerEntityToPlayer(UtilisateurEntity pe){
        Utilisateur p = new Utilisateur();
        p.setEmail(pe.getEmail());
        p.setPassword(pe.getPassword());
        p.setAdministrator(pe.isAdministrator());
        p.setBlocked(pe.isBlocked());
        return p;
    }
}
