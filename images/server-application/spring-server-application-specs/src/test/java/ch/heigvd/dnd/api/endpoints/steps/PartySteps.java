package ch.heigvd.dnd.api.endpoints.steps;
import ch.heigvd.dnd.ApiException;
import ch.heigvd.dnd.api.DefaultApi;
import ch.heigvd.dnd.api.dto.Party;
import ch.heigvd.dnd.api.endpoints.helpers.Environment;
import ch.heigvd.dnd.model.JwttokenLogic;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.CoreMatchers;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PartySteps {
    private Environment environment;
    private DefaultApi api;
    List<Party> parties;
    String partyName;

    public PartySteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
        this.parties = new LinkedList<>();
        partyName = "";
    }

    @Given("^i populate the party database$")
    public void i_populate_the_party_database() throws Throwable {
        UserDetails ud = new org.springframework.security.core.userdetails.User("normalOk"+ System.currentTimeMillis() +"@heig-vd.ch",
                "admin1234", new ArrayList<>());
        String token = new JwttokenLogic().generateToken(ud);
        api.getplayer(token, 0);
        for(int i = 0; i < 10; ++i){
            Party party = new Party();
            party.setId("party_" + i + "_" + System.currentTimeMillis());
            party.setReputation(i);
            api.createParty(token, party);
            parties.add(party);
        }
    }

    @When("^I get them to the /getallparties endpoint$")
    public void i_get_them_to_the_getallparties_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.getAllPartiesWithHttpInfo(environment.getToken(), environment.getPagination()));
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

    @Then("^I received a parties list$")
    public void i_received_a_parties_list() throws Throwable {
        assertThat(environment.getData().toString(), CoreMatchers.containsString("{mypage={nbEntries="));
        assertThat(environment.getData().toString(), CoreMatchers.containsString(", pagination=0.0, next="));
        assertThat(environment.getData().toString(), CoreMatchers.containsString(", previous=-}, parties=["));
        assertThat(environment.getData().toString(), CoreMatchers.containsString("]}"));
    }

    @Given("^I have a party name$")
    public void i_have_a_party_name() throws Throwable {
        partyName = parties.get(7).getId();
    }

    @When("^I get them to the /getparty endpoint$")
    public void i_get_them_to_the_getparty_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.getPartyWithHttpInfo(environment.getToken(), partyName, environment.getPagination()));
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

    @Then("^I received the party information$")
    public void i_received_the_party_information() throws Throwable {
        assertEquals("{mypage={nbEntries=0.0, pagination=0.0, next=-, previous=-}, party={id=" + partyName + ", reputation=7.0}, players=[]}", environment.getData().toString());
    }

    @When("^I get them to the /joinparty endpoint$")
    public void i_get_them_to_the_joinparty_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.joinPartyWithHttpInfo(environment.getToken(), partyName));
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

    @Then("^I received the party information with me inside$")
    public void i_received_the_party_information_with_me_inside() throws Throwable {
        assertEquals("{mypage={nbEntries=1.0, pagination=0.0, next=-, previous=-}, party={id=" + partyName + ", reputation=7.0}, players=[{email=" + new JwttokenLogic().getUsernameFromToken(environment.getToken()) + ", pseudo=Undifined, strength=6.0, dexterity=6.0, constitution=6.0, intelligence=6.0, wisdom=6.0, charisma=6.0, race=Undifined, classe=Undifined}]}", environment.getData().toString());
    }

    @When("^I get them to the /quitparty endpoint$")
    public void i_get_them_to_the_quitparty_endpoint() throws Throwable {
        try {
            environment.setLastApiResponse(api.quitPartyWithHttpInfo(environment.getToken(), partyName));
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

    @Given("^I have a false party name$")
    public void i_have_a_false_party_name() throws Throwable {
        partyName = "party_" + System.currentTimeMillis();
    }
}
