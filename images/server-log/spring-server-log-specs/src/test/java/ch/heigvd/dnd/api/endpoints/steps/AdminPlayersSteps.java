package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Simpleuser;
import ch.heigvd.dnd.api.dto.Utilisateur;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import ch.heigvd.dnd.model.JwttokenLogic;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AdminPlayersSteps {
    private Environment environment;
    private DefaultApi api;
    private Simpleuser player;
    private Integer page;

    public AdminPlayersSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I have a admin Player Token$")
    public void i_have_a_admin_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("adminOk@heig-vd.ch",
                "admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }

    @Given("^I have a pagination id$")
    public void i_have_a_pagination_id() throws Throwable {
        page = 0;
    }

    @When("^I get them to the /adminplayer endpoint$")
    public void i_get_them_to_the_adminplayer_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.adminplayerWithHttpInfo(environment.getToken(), page));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException( ).getCode( ));
        }
    }

    @Then("^I received a list of players$")
    public void i_received_a_list_of_players() throws Throwable {
        try{
            List<Utilisateur> users = (List<Utilisateur>) environment.getLastApiResponse( ).getData( );
            assertNotNull(users);
            assertTrue(users.size() <= 60);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Given("^I have a old admin Player Token$")
    public void i_have_a_old_admin_Player_Token() throws Throwable {
        environment.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbk9rQGhlaWctdmQuY2giLCJleHAiOjE1Nzg1OTkwNjAsImlhdCI6MTU3ODU4MTA2MH0.PL6aCrhh-BgYVbySQDQkmaM6rpeQvWQOe1b27iRjZbv9y-t36SUYO9rLYVR5Y6T7z2rvbogKupsig_ppVbrP7g");
    }

    @Given("^I have a username$")
    public void i_have_a_username() throws Throwable {
        player = new Simpleuser();
        player.setUserid("normalOk@heig-vd.ch");
    }

    @When("^I post them to the /adminplayer endpoint$")
    public void i_post_them_to_the_adminplayer_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.manageplayerWithHttpInfo(environment.getToken(), player));
            environment.setLastApiCallThrewException(false);
            environment.setLastApiException(null);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiCallThrewException(true);
            environment.setLastApiResponse(null);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException( ).getCode( ));
        }
    }

    @Given("^I have a locked admin Player Token$")
    public void i_have_a_locked_admin_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("adminLocked@heig-vd.ch",
                "admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }
}
