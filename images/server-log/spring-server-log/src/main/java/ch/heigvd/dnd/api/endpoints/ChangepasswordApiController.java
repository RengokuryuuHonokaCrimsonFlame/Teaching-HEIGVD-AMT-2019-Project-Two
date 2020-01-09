package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.ChangepasswordApi;
import ch.heigvd.dnd.api.dto.Passwords;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
import ch.heigvd.dnd.entities.UtilisateurEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.UtilisateurRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-08T10:05:49.499Z")

@Controller
public class ChangepasswordApiController implements ChangepasswordApi {

    @Autowired
    UtilisateurRepository playerRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity<Void> changepassword(@ApiParam(value = "name and password" ,required=true )  @Valid @RequestBody Passwords passwords, @ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<UtilisateurEntity> utilisateur = playerRepository.findById(userId);
        if(utilisateur.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(utilisateur.get().getEmail());
            if (utilisateur.get().isBlocked()) return ResponseEntity.status(HttpStatus.LOCKED).build();
            if (utilisateur.get().getPassword().equals(passwords.getOldpassword()) && new JwttokenLogic().validateToken(xDndToken, userDetails)){
                utilisateur.get().setPassword(passwords.getNewpassword());
                playerRepository.save(utilisateur.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
