package smart.hub.steps.api.core;

import configuration.AppConfiguration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ResultState;
import smart.hub.helpers.Constants;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.DebitPreauthRequestModel;
import smart.hub.mappings.api.models.response.proxiAPI.transaction.TransactionResponseModel;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class CoreSteps {

    private final Logger logger = Logger.getLogger(CoreSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @Given("^I make GET request to '(.*)'$")
    public void iMakeGETRequest(String endPoint) throws Exception {
        connectionService.getResponseForGET(endPoint);
    }

    @Given("^I make a POST request to the '(.*)' with the following request body '(.*)'")
    public void iMakeAPOSTRequestToTheWithTheFollowingRequestBody(String endpoint, String requestJson) throws Exception {
        DebitPreauthRequestModel transaction = connectionService.convertJsonToModel(requestJson, DebitPreauthRequestModel.class);
        connectionService.getResponseForPOST(endpoint, transaction, TransactionResponseModel.class);
    }

    @Given("^I make a POST request to the '(.*)' with the following json:$")
    public void iMakeAPOSTRequestToTheWithTheFollowingJson(String endpoint, List<String> json) throws Exception {
        connectionService.getResponseForPOST(endpoint, json.stream().findFirst().get());
    }

    @Given("^I make a POST request to the '(.*)' with the following json file '(.*)'$")
    public void iMakeAPOSTRequestToTheWithTheFollowingJsonFile(String endpoint, String fileName) throws Exception {
        connectionService.getResponseForPOST(endpoint, new File(Constants.pathToFiles + fileName));
    }

    @Then("^the status code should be '(.*)'$")
    public void theStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, resultState.getStatusCode());
    }

    @And("^the response message should be '(.*)'$")
    public void theResponseMessageShouldBe(String statusMessage) {
        assertEquals("Message was: " + statusMessage, statusMessage, resultState.getStatusMessage());
    }

    @Then("^the status codes of active code list should be '(.*)'$")
    public void theStatusCodeOfActiveCodesListShouldBe(int statusCode) {
        List<Integer> activeCodesList = Serenity.sessionVariableCalled("activeCodesList");
        for (int i = 0; i < activeCodesList.size(); i++)
            assertThat(statusCode).isEqualTo(activeCodesList.get(i));
    }

    @Then("^The body of respond is not empty$")
    public void responseIsNotEmpty() {
        assertThat(!resultState.getResult().isEmpty());
    }

    @Given("the body of the response contains '(.*)'$")
    public void theBodyOfTheResponseContains(boolean isValid) {
        boolean responseStatus = Boolean.valueOf(resultState.getResult());
        assertThat(responseStatus).isEqualTo(isValid);
    }
}
