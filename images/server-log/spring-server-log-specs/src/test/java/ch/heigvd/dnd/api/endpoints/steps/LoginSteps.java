package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Credentials;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class LoginSteps {
    private Environment environment;
    private DefaultApi api;
    private Credentials credentials;

    public LoginSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^I have a normal Player Credential$")
    public void i_have_a_normal_Player_Credential() throws Throwable {
        credentials = new Credentials();
        credentials.setId("normalOk@heig-vd.ch");
        credentials.setPassword("admin1234");
    }

    @Given("^I have a normal Player Credential \\(false password version\\)$")
    public void i_have_a_normal_Player_Credential_false_password_version() throws Throwable {
        credentials = new Credentials();
        credentials.setId("normalOk@heig-vd.ch");
        credentials.setPassword("admin12345");
    }

    @Given("^I have a normal Player Credential \\(false username version\\)$")
    public void i_have_a_normal_Player_Credential_false_username_version() throws Throwable {
        credentials = new Credentials();
        credentials.setId("normalOk" + System.currentTimeMillis() + "@heig-vd.ch");
        credentials.setPassword("admin1234");
    }

    @Given("^I have a locked Player Credential$")
    public void i_have_a_locked_Player_Credential() throws Throwable {
        credentials = new Credentials();
        credentials.setId("normalLocked@heig-vd.ch");
        credentials.setPassword("admin1234");
    }


    @When("^I post it to the /login endpoint$")
    public void i_post_it_to_the_login_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.loginWithHttpInfo(credentials));
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
}
