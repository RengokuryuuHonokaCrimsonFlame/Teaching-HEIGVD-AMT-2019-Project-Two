package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import ch.heigvd.dnd.model.JwttokenLogic;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CommonSteps {
    private Environment environment;
    private DefaultApi api;

    public CommonSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a Player server$")
    public void there_is_a_Player_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a normal Player Token$")
    public void i_have_a_normal_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("normalOk@heig-vd.ch",
                "admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }

    @Given("^I have a locked Player Token$")
    public void i_have_a_locked_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("normalLocked@heig-vd.ch",
                "admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }

    @Given("^I have a old Player Token$")
    public void i_have_a_old_Player_Token() throws Throwable {
        environment.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3JtYWxPa0BoZWlnLXZkLmNoIiwiZXhwIjoxNTc4NTk4NDUyLCJpYXQiOjE1Nzg1ODA0NTJ9.KL0sWp-4OX7wOsfFz68K5xRIJ_wrjOfnrfFWtKZD4gmQWmDbsP3PvU9wa53PxCuVJuRW3K-0s_ZGkPzbaDGo_A");
    }

    @Given("^I have an unknown Player Token$")
    public void i_have_an_unknown_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User(System.currentTimeMillis() +
                "@heig-vd.ch","admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }

    @Then("^I received a (\\d+) status code$")
    public void i_received_a_status_code(int arg1) throws Exception {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Then("^I have a x-token-dnd header$")
    public void i_have_a_x_token_dnd_header() throws Exception {
        try {
            assertNotNull(environment.getLastApiResponse( ).getHeaders( ).get("x-dnd-token"));
        }catch (NullPointerException e){
            assertTrue(false);
        }
    }

    @Then("^I do not have a x-token-dnd header$")
    public void i_do_not_have_a_x_token_dnd_header() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        try{
            environment.getLastApiResponse().getHeaders().get("x-dnd-token");
            assertTrue(false);
        } catch(NullPointerException e){
            assertTrue(true);
        }
    }

}
