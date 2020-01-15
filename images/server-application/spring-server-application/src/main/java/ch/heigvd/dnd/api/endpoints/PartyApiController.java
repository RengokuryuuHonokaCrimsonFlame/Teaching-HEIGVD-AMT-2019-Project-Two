package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.PartyApi;
import ch.heigvd.dnd.api.dto.Mypage;
import ch.heigvd.dnd.api.dto.Parties;
import ch.heigvd.dnd.api.dto.Party;
import ch.heigvd.dnd.entities.PartyEntity;
import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.PartyRepository;
import ch.heigvd.dnd.repositories.PlayerPartyRepository;
import ch.heigvd.dnd.repositories.PlayerRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-11T10:11:52.805Z")

@Controller
public class PartyApiController implements PartyApi {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    PlayerPartyRepository playerPartyRepository;

    public ResponseEntity<Object> getAllParties(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken, @Min(0)@ApiParam(value = "The number of the page") @Valid @RequestParam(value = "pagination", required = false) Integer pagination) {
        String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
        Optional<PlayerEntity> pe = playerRepository.findById(userId);
        if(new JwttokenLogic().isTokenExpired(xDndToken) || !pe.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Parties p = new Parties();
        p.setMypage(getMyPageParty(pagination));
        Pageable myPage = PageRequest.of(pagination, 60, Sort.by("email"));
        Page<PartyEntity> parts = partyRepository.findAll(myPage);
        List<Party> result = new LinkedList();
        for(PartyEntity pent : parts){
            result.add(playerEntityToPlayer(p));
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Object> getParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id,@Min(0)@ApiParam(value = "The number of the page") @Valid @RequestParam(value = "pagination", required = false) Integer pagination) {
        /*String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("\"{}\"", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }*/

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Object> joinParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id) {
        /*String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("\"{}\"", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }*/

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Object> quitParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id) {
        /*String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("\"{}\"", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }*/

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    private Mypage getMyPageParty(Integer page){
        Mypage p = new Mypage();
        p.setPagination(page);
        if(page <= 0){
            p.setPrevious("-");
        }else{
            p.setPrevious("ch.heigvd.dnd/party?page=" + (page - 1));
        }
        p.setNbEntries((int)partyRepository.count());
        if(page >= (p.getNbEntries()/60)){
            p.setNext("-");
        }else{
            p.setNext("ch.heigvd.dnd/party?page=" + (page + 1));
        }
        return p;
    }
}
