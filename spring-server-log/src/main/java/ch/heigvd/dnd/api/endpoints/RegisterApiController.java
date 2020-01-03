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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

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
        PlayerEntity newPlayerEntity = toPlayerEntity(player);
        playerRepository.save(newPlayerEntity);
        String id = newPlayerEntity.getId();

        JwttokenEntity newJwttokenEntity = toJwttokenEntity(player);
        jwttokenRepository.save(newJwttokenEntity);
        String token = newJwttokenEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/player")
                .buildAndExpand(newPlayerEntity.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private PlayerEntity toPlayerEntity(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getId());
        entity.setPassword(player.getPassword());
        entity.setStrength(entity.getStrength());
        entity.setDexterity(entity.getDexterity());
        entity.setConstitution(entity.getConstitution());
        entity.setIntelligence(entity.getIntelligence());
        entity.setWisdom(entity.getWisdom());
        entity.setCharisma(entity.getCharisma());
        entity.setRace(entity.getRace());
        entity.setClasse(entity.getClasse());
        return entity;
    }

    private JwttokenEntity toJwttokenEntity(Player player){
        JwttokenEntity entity = new JwttokenEntity();
        UserDetails userDetails = new UserDetails( ) {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return player.getPassword();
            }

            @Override
            public String getUsername() {
                return player.getId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        String id = entity.generateToken(userDetails);
        entity.setTemps((new Timestamp(new Date().getTime()).toString()));
        entity.setId(id);
        return entity;
    }
}
