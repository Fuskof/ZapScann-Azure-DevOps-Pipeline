package smart.hub.steps.api.smartHub;

import configuration.AppConfiguration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ConnectionState;
import smart.hub.components.ResultState;
import smart.hub.helpers.Helpers;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.mappings.api.models.request.payengine.BrowserInfo;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.DebitPreauthRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.Meta;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.ThreeDsData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static smart.hub.helpers.Constants.*;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class PayengineSteps {

    private final Logger logger = Logger.getLogger(PayengineSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionState connectionState;

    @Autowired
    private ResultState resultState;

    @Autowired
    private Helpers help;

    @Autowired
    private Environment env;

    @Given("I send a minimum {string} transaction to Payengine with specific fields:")
    public void iSendAMinimumTransactionToPayengineWithSpecificFields(String transactionType, @Transpose DebitPreauthRequestModel json) throws Exception {
        sendTransactionToPayEngine(transactionType, json);
    }

    @Given("I send a full {string} transaction to Payengine with specific fields:")
    public void iSendAFullTransactionToPayengineWithSpecificFields(String transactionType, DataTable dataTable) throws Exception {
        Map<String, String> originalMap = dataTable.asMap(String.class, String.class);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(originalMap);
        map.put("root", "full");
        DebitPreauthRequestModel json = JsonObjectGenerator.generateJsonObject(DebitPreauthRequestModel.class, map);
        sendTransactionToPayEngine(transactionType, json);
    }

    private void sendTransactionToPayEngine(String transactionType, DebitPreauthRequestModel json) throws Exception {
        if (!Objects.isNull(json.getMerchantOrderId())) {
            Serenity.setSessionVariable("merchantOrderId").to(json.getMerchantOrderId());
        }
        String endpoint;
        if (transactionType.equalsIgnoreCase("debit")) {
            endpoint = ENDPOINT_PAYENGINE_DEBIT;
        } else if (transactionType.equalsIgnoreCase("pre-auth") || transactionType.equalsIgnoreCase("preauth")) {
            endpoint = ENDPOINT_PAYENGINE_PREAUTH;
        } else {
            throw new IllegalArgumentException("Incorrect transaction type. Supported values: debit, pre-auth and preauth");
        }
        connectionState.setToken(env.getProperty("payengine.auth"));
        setBrowserInfoValue();
        String browserInfoId = Serenity.sessionVariableCalled("browserInfoId");
        Meta meta;
        ThreeDsData threeDsData;
        if (!Objects.isNull(json.getMeta())) {
            meta = (Meta) json.getMeta();
            if (!Objects.isNull(meta.getThreeDsData())) {
                threeDsData = (ThreeDsData) meta.getThreeDsData();
            } else {
                threeDsData = new ThreeDsData();
            }
        } else {
            meta = new Meta();
            threeDsData = new ThreeDsData();
        }
        threeDsData.setBrowserInfoId(browserInfoId);
        meta.setThreeDsData(threeDsData);
        json.setMeta(meta);
        connectionService.getResponseForPOSTPayEngine(endpoint, connectionService.convertToJson(json));
    }

    private void setBrowserInfoValue() throws Exception {
        BrowserInfo browserInfoRequest = new BrowserInfo();
        connectionService.getResponseForPOSTPayEngine(ENDPOINT_PAYENGINE_BROWSER_INFO, connectionService.convertToJson(browserInfoRequest));
        String browserInfoId = help.getNodeFromResponse("browserInfoId");
        Serenity.setSessionVariable("browserInfoId").to(browserInfoId);
    }
}
