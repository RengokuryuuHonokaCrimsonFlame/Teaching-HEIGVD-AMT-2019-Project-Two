package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.AdminplayerApi;
import ch.heigvd.dnd.api.dto.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-28T16:00:17.982Z")

@RestController
@CrossOrigin
public class AdminplayerApiController implements AdminplayerApi {
    @Override
    public ResponseEntity<List<Player>> adminplayer(String xDndToken) {
        return null;
    }

    @Override
    public ResponseEntity<List<Player>> manageplayer(String xDndToken, String id) {
        return null;
    }
}
