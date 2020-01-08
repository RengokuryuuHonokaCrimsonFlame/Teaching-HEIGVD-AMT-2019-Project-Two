package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.ApiResponse;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Player;


import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class RegisterSteps {

    private Environment environment;
    private DefaultApi api;
    private Player player;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public RegisterSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I have a player email$")
    public void i_have_a_player_email() throws Exception {
        player = new Player();
        player.setEmail("Bot" + System.currentTimeMillis() + "@heig-vd.ch");
        player.setAdministrator(false);
        player.setBlocked(false);
        player.setCharisma(666);
        player.setConstitution(666);
        player.setDexterity(666);
        player.setIntelligence(666);
        player.setStrength(666);
        player.setWisdom(666);
        player.setRace("Bot");
        player.setClasse("Bot");
        player.setPseudo(player.getEmail());
        player.setPassword("toto1234");
    }

    @Given("^there is a Player server$")
    public void there_is_a_Player_server() throws Throwable {
        assertNotNull(api);
    }

    @When("^I post it to the /register endpoint$")
    public void i_post_it_to_the_register_endpoint() throws Exception {
        try {
            lastApiResponse = api.registerWithHttpInfo(player);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^I received a (\\d+) status code$")
    public void i_received_a_status_code(int arg1) throws Exception {
        assertEquals(arg1, lastStatusCode);
    }

    @Then("^I have a x-token-dnd header$")
    public void i_have_a_x_token_dnd_header() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(lastApiResponse.getHeaders().get("x-dnd-token"));
    }

    @Then("^I do not have a x-token-dnd header$")
    public void i_do_not_have_a_x_token_dnd_header() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        try{
            lastApiResponse.getHeaders().get("x-dnd-token");
            assertTrue(false);
        } catch(NullPointerException e){
            assertTrue(true);
        }
    }
}