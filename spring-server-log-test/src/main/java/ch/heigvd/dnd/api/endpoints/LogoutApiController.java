package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.LogoutApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T08:27:44.937Z")

@Controller
public class LogoutApiController implements LogoutApi {

    public ResponseEntity<Object> logout() {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/login")
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
