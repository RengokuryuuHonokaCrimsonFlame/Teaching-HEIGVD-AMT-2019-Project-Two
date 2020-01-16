package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Party;
import ch.heigvd.dnd.api.dto.Player;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import ch.heigvd.dnd.model.JwttokenLogic;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class PlayerSteps {
    private Environment environment;
    private DefaultApi api;
    private Player player;
    private Party party;

    public PlayerSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.player = new Player();
        this.party = new Party();
    }

    @When("^I get them to the /getplayer endpoint$")
    public void i_get_them_to_the_getplayer_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.getplayerWithHttpInfo(environment.getToken(), environment.getPagination()));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            environment.setData(environment.getLastApiResponse().getData());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException( ).getCode( ));
        }
    }

    @Then("^I received a new player information$")
    public void i_received_a_new_player_information() throws Throwable {
        assertEquals("{mypage={nbEntries=0.0, pagination=0.0, next=-, previous=-}, player={email="
                + new JwttokenLogic().getUsernameFromToken(environment.getToken()) +
                ", pseudo=Undifined, strength=6.0, dexterity=6.0, constitution=6.0, intelligence=6.0, wisdom=6.0, charisma=6.0, race=Undifined, classe=Undifined}, parties=[]}"
                , environment.getData().toString());
    }

    @Given("^I update the players information$")
    public void i_update_the_players_information() throws Throwable {
        player.setRace("Test");
        player.setClasse("Testament");
        player.setWisdom(5);
        player.setDexterity(7);
        player.setConstitution(42);
        player.setCharisma(888);
        player.setIntelligence(9);
        player.setStrength(999);
        player.setPseudo("Bot");
        player.setEmail(new JwttokenLogic().getUsernameFromToken(environment.getToken()));
    }


    @Given("^I have a new Party$")
    public void i_have_a_new_Party() throws Throwable {
        party.setId("party_" + System.currentTimeMillis());
        party.setReputation(6);
    }

    @When("^I post them to the /updateplayer endpoint$")
    public void i_post_them_to_the_updateplayer_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.updateplayerWithHttpInfo(environment.getToken(), player));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            environment.setData(environment.getLastApiResponse().getData());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException( ).getCode( ));
        }
    }

    @When("^I post them to the /createParty$")
    public void i_post_them_to_the_createParty() throws Throwable {
        try {
            environment.setLastApiResponse(api.createPartyWithHttpInfo(environment.getToken(), party));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
            environment.setData(environment.getLastApiResponse().getData());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException( ).getCode( ));
        }
    }

    @Then("^I received a the update player information$")
    public void i_received_a_the_update_player_information() throws Throwable {
        assertEquals("{mypage={nbEntries=0.0, pagination=0.0, next=-, previous=-}, player={email="+
                new JwttokenLogic().getUsernameFromToken(environment.getToken())  +
                ", pseudo=Bot, strength=999.0, dexterity=7.0, constitution=42.0, intelligence=9.0, wisdom=5.0, charisma=888.0, race=Test, classe=Testament}, parties=[]}"
                , environment.getData().toString());
    }

    @Then("^I received the created party information$")
    public void i_received_the_created_party_information() throws Throwable {
        assertEquals("{id=" + party.getId() + ", reputation=6.0}"
                , environment.getData().toString());
    }
}
