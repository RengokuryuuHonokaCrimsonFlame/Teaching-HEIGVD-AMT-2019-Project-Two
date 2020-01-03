package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.RegisterApi;
import ch.heigvd.dnd.api.dto.Jwttoken;
import ch.heigvd.dnd.api.dto.Player;
import ch.heigvd.dnd.entities.JwttokenEntity;
import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.repositories.JwttokenRepository;
import ch.heigvd.dnd.repositories.PlayerRepository;
import io.swagger.annotations.*;
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
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class RegisterApiController implements RegisterApi {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    JwttokenRepository jwttokenRepository;

    @Override
    public ResponseEntity<Jwttoken> register(@ApiParam(value = "all player information" ,required=true )  @Valid @RequestBody Player player) {
        Optional<PlayerEntity> playerExists = playerRepository.findById(player.getEmail());
        if(playerExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        PlayerEntity newPlayerEntity = toPlayerEntity(player);
        playerRepository.save(newPlayerEntity);
        String id = newPlayerEntity.getEmail();

        JwttokenEntity newJwttokenEntity = toJwttokenEntity(player);
        jwttokenRepository.save(newJwttokenEntity);
        String token = newJwttokenEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/player")
                .buildAndExpand(newPlayerEntity.getEmail()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header("x-dnd-token", token).build();
    }

    private PlayerEntity toPlayerEntity(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setEmail(player.getEmail());
        entity.setPseudo(player.getPseudo());
        entity.setPassword(player.getPassword());
        entity.setStrength(player.getStrength());
        entity.setDexterity(player.getDexterity());
        entity.setConstitution(player.getConstitution());
        entity.setIntelligence(player.getIntelligence());
        entity.setWisdom(player.getWisdom());
        entity.setCharisma(player.getCharisma());
        entity.setRace(player.getRace());
        entity.setClasse(player.getClasse());
        entity.setAdministrator(player.getAdministrator());
        entity.setBlocked(player.getBlocked());
        return entity;
    }

    private JwttokenEntity toJwttokenEntity(Player player){
        JwttokenEntity entity = new JwttokenEntity();
        String id = entity.generateToken(player.getEmail());
        entity.setTemps((new Timestamp(new Date().getTime()).toString()));
        entity.setId(id);
        return entity;
    }
}
