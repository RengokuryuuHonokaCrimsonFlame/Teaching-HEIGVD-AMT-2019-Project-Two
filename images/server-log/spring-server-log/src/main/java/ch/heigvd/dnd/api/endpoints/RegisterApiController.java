package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.RegisterApi;
import ch.heigvd.dnd.api.dto.Jwttoken;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.api.dto.Utilisateur;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
import ch.heigvd.dnd.entities.UtilisateurEntity;
import ch.heigvd.dnd.repositories.UtilisateurRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class RegisterApiController implements RegisterApi {

    @Autowired
    UtilisateurRepository playerRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    public ResponseEntity<Jwttoken> register(@ApiParam(value = "all player information" ,required=true )  @Valid @RequestBody Utilisateur player) {
        Optional<UtilisateurEntity> playerExists = playerRepository.findById(player.getEmail());
        if(playerExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UtilisateurEntity newPlayerEntity = toPlayerEntity(player);
        playerRepository.save(newPlayerEntity);
        String id = newPlayerEntity.getEmail();

        JwttokenLogic newJwttokenLogic = toJwttoken(player);
        String token = newJwttokenLogic.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/player")
                .buildAndExpand(newPlayerEntity.getEmail()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header("x-dnd-token", token).build();
    }

    private UtilisateurEntity toPlayerEntity(Utilisateur player) {
        UtilisateurEntity entity = new UtilisateurEntity();
        entity.setEmail(player.getEmail());
        entity.setPassword(player.getPassword());
        entity.setAdministrator(player.getAdministrator());
        entity.setBlocked(player.getBlocked());
        return entity;
    }

    private JwttokenLogic toJwttoken(Utilisateur player){
        JwttokenLogic entity = new JwttokenLogic();
        String id = entity.generateToken(jwtUserDetailsService.loadUserByUsername(player.getEmail()));
        entity.setTemps((new Timestamp(new Date().getTime()).toString()));
        entity.setId(id);
        return entity;
    }
}
