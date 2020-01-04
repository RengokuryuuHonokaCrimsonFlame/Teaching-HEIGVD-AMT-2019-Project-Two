package ch.heigvd.dnd.api.endpoints;


import ch.heigvd.dnd.api.LoginApi;
import ch.heigvd.dnd.api.dto.Credentials;
import ch.heigvd.dnd.api.dto.Jwttoken;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
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
import java.util.Date;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class LoginApiController implements LoginApi {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    JwttokenRepository jwttokenRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity<Jwttoken> login(@ApiParam(value = "name and password" ,required=true )  @Valid @RequestBody Credentials credentials) {
        Optional<PlayerEntity> player = playerRepository.findById(credentials.getId());
        if(player.isPresent() && player.get().getPassword().equals(credentials.getPassword())){
            if(player.get().isBlocked()) return ResponseEntity.status(HttpStatus.LOCKED).build();
            JwttokenEntity newJwttokenEntity = toJwttokenEntity(credentials);
            jwttokenRepository.save(newJwttokenEntity);
            String token = newJwttokenEntity.getId();

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/player")
                    .buildAndExpand(player.get().getEmail()).toUri();
            return ResponseEntity.status(HttpStatus.OK).header("x-dnd-token", token).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    private JwttokenEntity toJwttokenEntity(Credentials crednetials){
        JwttokenEntity entity = new JwttokenEntity();
        String id = entity.generateToken(jwtUserDetailsService.loadUserByUsername(crednetials.getId()));
        entity.setTemps((new Timestamp(new Date().getTime()).toString()));
        entity.setId(id);
        return entity;
    }
}
