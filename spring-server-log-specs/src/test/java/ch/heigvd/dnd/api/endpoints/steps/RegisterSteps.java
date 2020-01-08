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


    @When("^I post it to the /register endpoint$")
    public void i_post_it_to_the_register_endpoint() throws Exception {
        try {
            environment.setLastApiResponse(api.registerWithHttpInfo(player));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }
}