package smart.hub.steps.api.smartHub;

import configuration.AppConfiguration;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ResultState;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TransactionStatus;
import smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument.PaymentInstrumentRequest;
import smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument.PaymentPI;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.BaseTransactionRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.CancelCaptureRefundRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.DebitPreauthRequestModel;
import smart.hub.mappings.api.models.response.proxiAPI.transaction.CancelCaptureRefundResponseModel;
import smart.hub.mappings.api.models.response.proxiAPI.transaction.TransactionResponseModel;

import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static smart.hub.helpers.Constants.*;


@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class PayengineSimulatorSteps {

    private final Logger logger = Logger.getLogger(PayengineSimulatorSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;


    @Given("I send a debit trx to Payengine simulator with the following fields")
    public void iSendADebitTrxToPayengineSimulatorWithTheFollowingFields(@Transpose DebitPreauthRequestModel json) throws Exception {
        connectionService.getResponseForPOST(ENDPOINT_PAYENGINE_SIMULATOR, connectionService.convertToJson(json));
    }

    @Given("I send a pre-auth trx to Payengine simulator with the following fields")
    public void iSendAPreAuthTrxToPayengineSimulatorWithTheFollowingFields(@Transpose DebitPreauthRequestModel json) throws Exception {
        connectionService.getResponseForPOST(ENDPOINT_PAYENGINE_SIMULATOR, connectionService.convertToJson(json));
    }

    @Given("I send a {string} trx to Payengine simulator with the following fields")
    public void iSendATrxToPayengineSimulatorWithTheFollowingFields(String transactionType, @Transpose CancelCaptureRefundRequestModel json) throws Exception {
        String endpoint;
        BaseTransactionRequestModel baseTransactionRequestModel = Objects.isNull(json) ?
                JsonObjectGenerator.generateJsonObject(CancelCaptureRefundRequestModel.class) :
                json;

        switch (transactionType.toLowerCase()) {
            case "cancel":
                endpoint = ENDPOINT_PE_CANCEL_TRANSACTION.replace("{orderId}", "oid").replace("{transactionId}", "tid");
                break;
            case "capture":
                endpoint = ENDPOINT_PE_CAPTURE_TRANSACTION.replace("{orderId}", "oid").replace("{transactionId}", "tid");
                break;
            case "refund":
                endpoint = ENDPOINT_PE_REFUND_TRANSACTION.replace("{orderId}", "oid").replace("{transactionId}", "tid");
                break;
            default:
                throw new IllegalArgumentException("Incorrect transaction type. Supported values: cancel, capture and refund");
        }
        connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(baseTransactionRequestModel));
    }

    @And("the body of the {string} response contains the appropriate values for status")
    public  void theBodyOfTheResponseContainsTheAppropriateValuesForStatus(String transactionType, Map<String, String> table) {
        if (transactionType.toLowerCase().equals("cancel")
                || transactionType.toLowerCase().equals("capture")
                || transactionType.toLowerCase().equals("refund")) {
            String status = table.get("status");
            CancelCaptureRefundResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), CancelCaptureRefundResponseModel.class);
            if (response.getStatus() != null && response.getStatus().name() != "") {
                assertEquals("status" + status, status, response.getStatus().name());
            }
            return;
        }
        if (transactionType.toLowerCase().equals("debit") || transactionType.toLowerCase().equals("pre-auth")) {
            String redirectUrl = table.get("redirectUrl");
            String status = table.get("transactions.status");
            TransactionResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseModel.class);
            if (response.getTransactions() != null && response.getTransactions().length != 0) {
                assertEquals("status " + status, status, response.getTransactions()[0].getStatus().name());
                if (response.getTransactions()[0].getStatus() == TransactionStatus.PENDING) {
                    assertEquals("Fake url: " + redirectUrl, redirectUrl, response.getRedirectUrl());
                }
                return;
            }
            throw new RuntimeException("The response does not contain any transactions");
        }
    }

    @Given("I send a post payment instrument request with random fields to Payengine simulator")
    public void iSendAPostPaymentInstrumentRequestWithRandomFieldsToPayengineSimulator() throws Exception {
        PaymentInstrumentRequest json = JsonObjectGenerator.generateJsonObject(PaymentInstrumentRequest.class);
        connectionService.getResponseForPOST(ENDPOINT_PAYENGINE_PI, connectionService.convertToJson(json));
    }

    @Given("I send a post payment instrument request with the following fields to Payengine simulator")
    public void iSendAPostPaymentInstrumentRequestWithTheFollowingFieldsToPayengineSimulator(@Transpose PaymentInstrumentRequest json) throws Exception {
        connectionService.getResponseForPOST(ENDPOINT_PAYENGINE_PI, connectionService.convertToJson(json));
    }

    @Given("I patch a payment instrument with id {string} having random fields to Payengine simulator")
    public void iPatchAPaymentInstrumentWithIdStringHavingRandomFieldsToPayengineSimulator(String paymentInstrumentId, @Transpose PaymentInstrumentRequest json) throws Exception {
        String body = String.format(
        "{ \"payment\": {" +
        "        \"verification\": %s, " +
        "        \"cardNumber\": %s" +
        "}}",
        ((PaymentPI)json.getPayment()).getVerification().toString(),
        ((PaymentPI)json.getPayment()).getCardNumber().toString());

        connectionService.getResponseForPATCH(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", paymentInstrumentId), body);
    }

    @Given("I patch payment instrument request with the following fields to Payengine simulator")
    public void iPatchPaymentInstrumentRequestWithTheFollowingFieldsToPayengineSimulator(@Transpose PaymentInstrumentRequest json) throws Exception {
        connectionService.getResponseForPATCH(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", "oid"), connectionService.convertToJson(json));
    }

    @Given("I send a get payment instrument request with a valid PIid to Payengine simulator")
    public void iSendAGetPaymentInstrumentRequestWithAValidPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", "oid"));
    }
    @Given("I send a get payment instrument request with an invalid PIid to Payengine simulator")
    public void iSendAGetPaymentInstrumentRequestWithAnInvalidPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}",
                "3UZiavUiAbFiD3v5KW4Bgj6nBRek1lgJEigZJVBkDfwuCgWQ9wvXdQJpJP0v9zNbD0cBp4SZBnVbFf7U7" +
                "9N3RJuqiuiO0rS1ADEe0G9F7gQMIxIqciqZg1zwPSfbjsxHMZcUwnRGIExCCvEEhJIy9CJW7EDbnCFKZfoqW"+
                "i9f2SFLGsSQvvOmaUJvg2LQ5YxHXw9DwDsKQLYA4RljB6SdkbvIERi0vm7B9akPwV99udTUW70mLCiuVuGUnMfxqSGA"));
    }

    @Given("I send a get payment instrument request with a specific PIid to Payengine simulator")
    public void iSendAGetPaymentInstrumentRequestWithASpecificPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", "paymentinstrument_815dzdeart"));
    }

    @Given("I send a {string} getAll payment instruments request to Payengine simulator")
    public void iSendAGetAllPaymentInstrumentsRequestToPayengineSimulator(String paymentInstrumentsStatus) throws Exception {
        String queryParams = "?notFound=";
        switch (paymentInstrumentsStatus){
            case "success":
                queryParams+="false";
                break;
            case "failure":
                queryParams+="true";
                break;
            default: throw new Exception("Invalid payment instrument status");
        }
        connectionService.getResponseForGET(ENDPOINT_PAYENGINE_PI.concat(queryParams));
    }

    @Given("I send a delete payment instrument request with a valid PIid to Payengine simulator")
    public void iSendADeletePaymentInstrumentRequestWithAValidPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForDELETE(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", "oid"));
    }

    @Given("I send a delete payment instrument request with an invalid PIid to Payengine simulator")
    public void iSendADeletePaymentInstrumentRequestWithAnInvalidPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForDELETE(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}",
                "3UZiavUiAbFiD3v5KW4Bgj6nBRek1lgJEigZJVBkDfwuCgWQ9wvXdQJpJP0v9zNbD0cBp4SZBnVbFf7U7"+
                          "9N3RJuqiuiO0rS1ADEe0G9F7gQMIxIqciqZg1zwPSfbjsxHMZcUwnRGIExCCvEEhJIy9CJW7EDbnCFKZfoqW"+
                          "i9f2SFLGsSQvvOmaUJvg2LQ5YxHXw9DwDsKQLYA4RljB6SdkbvIERi0vm7B9akPwV99udTUW70mLCiuVuGUnMfxqSGA"));
    }

    @Given("I send a delete payment instrument request with a specific PIid to Payengine simulator")
    public void iSendADeletePaymentInstrumentRequestWithASpecificPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForDELETE(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", "paymentinstrument_725dzbxast"));
    }

    @Given("I send a delete payment instrument request without PIid to Payengine simulator")
    public void iSendADeletePaymentInstrumentRequestWithoutPIidToPayengineSimulator() throws Exception {
        connectionService.getResponseForDELETE(ENDPOINT_PAYENGINE_PI_ID.replace("{paymentInstrumentId}", ""));
    }


}