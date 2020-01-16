package ch.heigvd.dnd.api.endpoints;

import ch.heigvd.dnd.api.PartyApi;
import ch.heigvd.dnd.api.dto.*;
import ch.heigvd.dnd.entities.PartyEntity;
import ch.heigvd.dnd.entities.PlayerEntity;
import ch.heigvd.dnd.entities.PlayerPartyEntity;
import ch.heigvd.dnd.model.JwttokenLogic;
import ch.heigvd.dnd.repositories.PartyRepository;
import ch.heigvd.dnd.repositories.PlayerPartyRepository;
import ch.heigvd.dnd.repositories.PlayerRepository;
import io.jsonwebtoken.ExpiredJwtException;
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
        try {
            String userId = new JwttokenLogic( ).getUsernameFromToken(xDndToken);
            Optional<PlayerEntity> pe = playerRepository.findById(userId);
            if (!pe.isPresent( )) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build( );
            }
            Parties p = new Parties( );
            p.setMypage(getMyPageParty(pagination));
            Pageable myPage = PageRequest.of(pagination, 60, Sort.by("id"));
            Page<PartyEntity> parts = partyRepository.findAll(myPage);
            List<Party> result = new LinkedList( );
            for (PartyEntity pent : parts) {
                result.add(getPartyFromPartyEntity(pent));
            }
            p.setParties(result);
            return ResponseEntity.status(HttpStatus.OK).body(p);
        }catch(ExpiredJwtException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<Object> getParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id,@Min(0)@ApiParam(value = "The number of the page") @Valid @RequestParam(value = "pagination", required = false) Integer pagination) {
        try {
            String userId = new JwttokenLogic( ).getUsernameFromToken(xDndToken);
            Optional<PlayerEntity> pe = playerRepository.findById(userId);
            if (!pe.isPresent( )) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build( );
            }
            Optional<PartyEntity> party = partyRepository.findById(id);
            if (party.isPresent( )) {
                Partypage pp = new Partypage( );
                pp.setParty(getPartyFromPartyEntity(party.get( )));
                pp.setMypage(getMyPage(pagination, party.get( ).getId( )));
                pp.setPlayers(getPartyPlayers(pagination, party.get( ).getId( )));
                return ResponseEntity.status(HttpStatus.OK).body(pp);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build( );
        }catch(ExpiredJwtException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<Object> joinParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id) {
        try{
            String userId = new JwttokenLogic().getUsernameFromToken(xDndToken);
            Optional<PlayerEntity> ple = playerRepository.findById(userId);
            Optional<PartyEntity> pae = partyRepository.findById(id);
            if(ple.isPresent() && pae.isPresent()){
                Optional<PlayerPartyEntity> check = playerPartyRepository.findByPartyIdAndPlayerEmail(id,userId);
                if(!check.isPresent()){
                    PlayerPartyEntity ppe = new PlayerPartyEntity();
                    Optional<PlayerEntity> player = playerRepository.findById(userId);
                    Optional<PartyEntity> party = partyRepository.findById(id);
                    ppe.setParty(party.get());
                    ppe.setPlayer(player.get());
                    playerPartyRepository.save(ppe);
                }
                return getParty(xDndToken, id, 0);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(ExpiredJwtException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<Object> quitParty(@ApiParam(value = "header that contain a JwtToken" ,required=true) @RequestHeader(value="x-dnd-token", required=true) String xDndToken,@ApiParam(value = "ID of the party to return",required=true) @PathVariable("id") String id) {
        try {
            String userId = new JwttokenLogic( ).getUsernameFromToken(xDndToken);
            Optional<PlayerEntity> ple = playerRepository.findById(userId);
            Optional<PartyEntity> pae = partyRepository.findById(id);
            if (ple.isPresent( ) && pae.isPresent( )) {
                Optional<PlayerPartyEntity> check = playerPartyRepository.findByPartyIdAndPlayerEmail(id, userId);
                if (check.isPresent( )) {
                    playerPartyRepository.deleteById(check.get( ).getId( ));
                }
                return getParty(xDndToken, id, 0);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build( );
        }catch(ExpiredJwtException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private List<Player> getPartyPlayers(Integer page, String id){
        Pageable myPage = PageRequest.of(page, 60, Sort.by("player_email"));
        Page<PlayerPartyEntity> relations = playerPartyRepository.findByPartyId(id, myPage);
        List<Player> players = new LinkedList<>();
        for(PlayerPartyEntity ppe : relations){

            Optional<PlayerEntity> player = playerRepository.findById(ppe.getPlayer().getEmail());
            players.add(PlayerApiController.getPlayerFromPlayerEntity(player.get()));
        }
        return players;
    }

    private Mypage getMyPageParty(Integer page){
        Mypage p = new Mypage();
        p.setPagination(page);
        if(page <= 0){
            p.setPrevious("-");
        }else{
            p.setPrevious("www.heigvd-dnd.ch/party?page=" + (page - 1));
        }
        p.setNbEntries((int)partyRepository.count());
        if(page >= (p.getNbEntries()/60)){
            p.setNext("-");
        }else{
            p.setNext("www.heigvd-dnd.ch/party?page=" + (page + 1));
        }
        return p;
    }

    private Mypage getMyPage(Integer page, String id){
        Mypage p = new Mypage();
        p.setPagination(page);
        if(page <= 0){
            p.setPrevious("-");
        }else{
            p.setPrevious("www.heigvd-dnd.ch/player?page=" + (page - 1));
        }
        p.setNbEntries(playerPartyRepository.countByPartyId(id));
        if(page >= (p.getNbEntries()/60)){
            p.setNext("-");
        }else{
            p.setNext("www.heigvd-dnd.ch/player?page=" + (page + 1));
        }
        return p;
    }


    static Party getPartyFromPartyEntity(PartyEntity pe){
        Party p = new Party();
        p.setId(pe.getId());
        p.setReputation(pe.getReputation());
        return p;
    }

    static PartyEntity getPartyEntityFromParty(Party p){
        PartyEntity pe = new PartyEntity();
        pe.setId(p.getId());
        pe.setReputation(p.getReputation());
        return pe;
    }
}
