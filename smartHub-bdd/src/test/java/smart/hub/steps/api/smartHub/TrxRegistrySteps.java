package smart.hub.steps.api.smartHub;

import configuration.AppConfiguration;
import io.cucumber.java.en.And;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.DatabaseService;
import smart.hub.components.ResultState;
import smart.hub.mappings.db.models.transactionRegistry.request.TransactionRequest;
import smart.hub.mappings.db.models.transactionRegistry.response.TransactionResponseModel;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class TrxRegistrySteps {

    private Logger logger = Logger.getLogger(TrxRegistrySteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ResultState resultState;


    @And("the {string} request should be recorded in Trx Registry")
    public void theRequestShouldBeRecordedInTrxRegistry(String transactionType) {
        List<TransactionRequest> result;
        String query;
        switch (transactionType.toLowerCase())
        {
            case "debit":
            case "preauth":
            case "pre-auth":
                query = "TransactionRegistry.TransactionRequests.find({'SerializedRequest':%s})";
                break;
            case "cancel":
                query = "TransactionRegistry.CancelTransactionRequests.find({'SerializedRequest':%s})";
                break;
            case "capture":
                query = "TransactionRegistry.CaptureTransactionRequests.find({'SerializedRequest':%s})";
                break;
            case "refund":
                query = "TransactionRegistry.RefundTransactionRequests.find({'SerializedRequest':%s})";
                break;
            default:
                throw new IllegalArgumentException("Incorrect transaction type. Supported values: debit, pre-auth/preauth, cancel, capture and refund");
        }
        result = databaseService.executeQuery(TransactionRequest.class, query, resultState.getRequestJson());
        assertThat("Trx request recorded", result.size(), is(1));
    }


    @And("the {string} response should be recorded in Trx Registry")
    public void theTrxResponseShouldBeRecordedInTrxRegistry(String transactionType) {
        List<TransactionResponseModel> result;
        String query;
        switch (transactionType.toLowerCase())
        {
            case "debit":
            case "preauth":
            case "pre-auth":
                query = "TransactionRegistry.TransactionResponses.find({'SerializedResponse':%s})";
                break;
            case "cancel":
                query = "TransactionRegistry.CancelTransactionResponses.find({'SerializedResponse':%s})";
                break;
            case "capture":
                query = "TransactionRegistry.CaptureTransactionResponses.find({'SerializedResponse':%s})";
                break;
            case "refund":
                query = "TransactionRegistry.RefundTransactionResponses.find({'SerializedResponse':%s})";
                break;
            default:
                throw new IllegalArgumentException("Incorrect transaction type. Supported values: debit, pre-auth/preauth, cancel, capture and refund");
        }
        result = databaseService.executeQuery(TransactionResponseModel.class, query, resultState.getResult());
        assertThat("Trx response recorded", result.size(), is(1));
    }
}