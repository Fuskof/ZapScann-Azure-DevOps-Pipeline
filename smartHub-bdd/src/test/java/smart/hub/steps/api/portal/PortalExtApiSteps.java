package smart.hub.steps.api.portal;

import configuration.AppConfiguration;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ResultState;

import static smart.hub.helpers.Constants.ENDPOINT_GET_RULES_BY_MERCHANT_ID;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class PortalExtApiSteps {

    private final Logger logger = Logger.getLogger(PortalExtApiSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @Given("I retrieve all the rules for '(.*)'$")
    public void iRetrieveAllTheRulesForGetRulesByMerchantId(String endPoint) throws Exception {
        connectionService.getResponseForGET(ENDPOINT_GET_RULES_BY_MERCHANT_ID + endPoint);
    }
}

