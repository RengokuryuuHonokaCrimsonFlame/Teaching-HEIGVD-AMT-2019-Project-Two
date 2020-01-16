package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import ch.heigvd.dnd.model.JwttokenLogic;
import cucumber.api.PendingException;
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

    @Given("^there is an application server$")
    public void there_is_an_application_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a pagination id$")
    public void i_have_a_pagination_id() throws Throwable {
        environment.setPagination(0);
    }

    @Given("^I have a Player Token$")
    public void i_have_a_Player_Token() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("normalOk"+ System.currentTimeMillis() +"@heig-vd.ch",
                "admin1234", new ArrayList<>());
        environment.setToken(new JwttokenLogic().generateToken(ud));
    }

    @Given("^I have a Player old Token$")
    public void i_have_a_Player_old_Token() throws Throwable {
        environment.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3JtYWxPa0BoZWlnLXZkLmNoIiwiZXhwIjoxNTc4NTk4NDUyLCJpYXQiOjE1Nzg1ODA0NTJ9.KL0sWp-4OX7wOsfFz68K5xRIJ_wrjOfnrfFWtKZD4gmQWmDbsP3PvU9wa53PxCuVJuRW3K-0s_ZGkPzbaDGo_A");
    }

    @Then("^I received a (\\d+) status code$")
    public void i_received_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }
}
