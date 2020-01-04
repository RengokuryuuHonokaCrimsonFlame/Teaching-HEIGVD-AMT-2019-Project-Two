package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.LogoutApi;
import ch.heigvd.dnd.repositories.JwttokenRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class LogoutApiController implements LogoutApi {

    @Autowired
    JwttokenRepository jwttokenRepository;

    @Override
    public ResponseEntity<Object> logout(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken) {

        jwttokenRepository.deleteById(xDndToken);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
