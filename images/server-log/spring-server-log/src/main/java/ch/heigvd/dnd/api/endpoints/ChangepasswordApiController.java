package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.ChangepasswordApi;
import ch.heigvd.dnd.api.dto.Jwttoken;
import ch.heigvd.dnd.configuration.JwtUserDetailsService;
import ch.heigvd.dnd.entities.UtilisateurEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.UtilisateurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-08T10:05:49.499Z")

@Controller
public class ChangepasswordApiController implements ChangepasswordApi {

    @Autowired
    UtilisateurRepository playerRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity<Void> changepassword(@ApiParam(value = "" ,required=true) @RequestHeader(value="oldpassword", required=true) String oldpassword, @ApiParam(value = "" ,required=true) @RequestHeader(value="newpassword", required=true) String newpassword, @ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<UtilisateurEntity> utilisateur = playerRepository.findById(userId);
        if(utilisateur.isPresent()) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(utilisateur.get().getEmail());
            if (utilisateur.get().isBlocked()) return ResponseEntity.status(HttpStatus.LOCKED).build();
            if (utilisateur.get().getPassword().equals(oldpassword)){
                utilisateur.get().setPassword(newpassword);
                playerRepository.save(utilisateur.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
