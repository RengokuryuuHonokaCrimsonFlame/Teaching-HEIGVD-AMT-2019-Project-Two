package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.SigninApi;
import ch.heigvd.dnd.api.dto.User;
import ch.heigvd.dnd.entities.UserEntity;
import ch.heigvd.dnd.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T08:27:44.937Z")

@Controller
public class SigninApiController implements SigninApi {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> signin(@ApiParam(value = "the user (name and password)" ,required=true )  @Valid @RequestBody User user) {
       UserEntity newUserEntity = toUserEntity(user);
       userRepository.save(newUserEntity);
       String username = newUserEntity.getUsername();

       URI location = ServletUriComponentsBuilder
               .fromCurrentRequest().path("/user/{username}")
               .buildAndExpand(newUserEntity.getUsername()).toUri();

       return ResponseEntity.created(location).build();
    }

    private UserEntity toUserEntity(User user){
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
