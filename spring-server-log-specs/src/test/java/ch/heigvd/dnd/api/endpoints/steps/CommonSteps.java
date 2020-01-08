package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

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
    @Then("^I received a (\\d+) status code$")
    public void i_received_a_status_code(int arg1) throws Exception {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Then("^I have a x-token-dnd header$")
    public void i_have_a_x_token_dnd_header() throws Exception {
        // Write code here that turns the phrase above into concrete actions
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
