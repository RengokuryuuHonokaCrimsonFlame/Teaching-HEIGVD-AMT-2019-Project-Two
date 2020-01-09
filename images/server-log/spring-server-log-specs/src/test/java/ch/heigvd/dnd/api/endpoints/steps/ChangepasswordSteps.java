package ch.heigvd.dnd.api.endpoints.steps;

import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Credentials;
import ch.heigvd.dnd.api.dto.Passwords;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class ChangepasswordSteps {
    private Environment environment;
    private DefaultApi api;
    private Passwords passwords;

    public ChangepasswordSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.passwords = new Passwords();
    }

    @Given("^I have a old password$")
    public void i_have_a_old_password() throws Throwable {
        passwords.setOldpassword("admin1234");
    }

    @Given("^I have a new password$")
    public void i_have_a_new_password() throws Throwable {
        passwords.setNewpassword("admin1235");
    }

    @Given("^I have a wrong password$")
    public void i_have_a_wrong_password() throws Throwable {
        passwords.setOldpassword("admin1233");
    }

    @When("^I post them to the /changepassword endpoint$")
    public void i_post_them_to_the_changepassword_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.changepasswordWithHttpInfo(environment.getToken(), passwords));
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

    @Given("^I have a old password reverse$")
    public void i_have_a_old_password_reverse() throws Throwable {
        passwords.setOldpassword("admin1235");
    }

    @Given("^I have a new password reverse$")
    public void i_have_a_new_password_reverse() throws Throwable {
        passwords.setNewpassword("admin1234");
    }
}