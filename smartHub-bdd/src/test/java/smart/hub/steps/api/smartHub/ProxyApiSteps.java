package smart.hub.steps.api.smartHub;

import configuration.AppConfiguration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ConnectionState;
import smart.hub.components.ResultState;
import smart.hub.helpers.Conversions;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.helpers.generators.JsonValueGenerator;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ChannelType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.Currency;
import smart.hub.mappings.api.enums.proxyAPI.transaction.ProductType;
import smart.hub.mappings.api.enums.proxyAPI.transaction.TransactionResponseErrorCodes;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.CancelCaptureRefundRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.DebitPreauthRequestModel;
import smart.hub.mappings.api.models.response.proxiAPI.transaction.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static smart.hub.helpers.Constants.*;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class ProxyApiSteps {


    private Logger logger = Logger.getLogger(ProxyApiSteps.class.getName());

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionState connectionState;

    @Autowired
    private ResultState resultState;

    @Autowired
    private Environment env;

    @Given("^I convert the response to json for Proxy API$")
    public void iConvertTheResponseToJsonForProxyApi() {
        TransactionResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseModel.class);
    }

    @Given("I send a {string} transaction request with random fields")
    public void iSendATransactionRequestWithRandomFields(String transactionType) throws Exception {
        sendTransactionRequest(transactionType, null, true, null, null);
    }

    @Given("I send a {string} transaction request with specific fields:")
    public void iSendADebitPreauthTransactionRequestWithSpecificFields(String transactionType, @Transpose DebitPreauthRequestModel json) throws Exception {
        sendTransactionRequest(transactionType, json, true, null, null);
    }

    @When("I {string} a transaction with specific fields:")
    public void iCancelCaptureRefundTransactionRequestWithSpecificFields(String transactionType, @Transpose CancelCaptureRefundRequestModel json) throws Exception {
        sendTransactionRequest(transactionType, json, true, null, null);
    }

    @When("I send a {string} transaction request with custom path parameters:")
    public void iSendCancelCaptureRefundRequestWithCustomPathParams (String transactionType, DataTable input) throws Exception {
        Map<String, String> originalMap = input.asMap(String.class, String.class);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(originalMap);
        JsonValueGenerator jsonValueGenerator = new JsonValueGenerator();
        String orderId = jsonValueGenerator.generateJsonValue(map.get("orderId")).getStringValue();
        jsonValueGenerator = new JsonValueGenerator();
        String transactionId = jsonValueGenerator.generateJsonValue(map.get("transactionId")).getStringValue();
        sendTransactionRequest(transactionType, null, true, orderId, transactionId);
    }

    @Given("I send a {string} transaction request with no authorization")
    public void iSendATransactionRequestWithNoAuthorization(String transactionType) throws Exception {
        sendTransactionRequest(transactionType, null, false, null, null);
    }

    @Then("the response contains error code {int}")
    public void validateSingleResponseErrorCode(int fieldErrorCode) {
        String fieldErrorMessage = TransactionResponseErrorCodes.valueOf("ERROR_CODE_" + fieldErrorCode).getErrorMessage();
        TransactionResponseErrorModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseErrorModel.class);
        Map<Integer, String> errorsMap = new HashMap<>();
        for (TransactionErrorElement error : response.getErrors()) {
            errorsMap.put(error.getCode(), error.getMessage());
        }
        assertThat("Field error code not found in the response", errorsMap.keySet(), hasItem(fieldErrorCode));
        assertThat("Incorrect field error message", errorsMap.get(fieldErrorCode), is(fieldErrorMessage));
    }

    @Then("the response contains error code {int} for field {string}")
    public void validateSingleResponseErrorCodeAndErrorMessage(int fieldErrorCode, String fieldPath) {
        int errorCode = 12000;
        String errorMessage = TransactionResponseErrorCodes.valueOf("ERROR_CODE_" + errorCode).getErrorMessage();
        String fieldErrorMessage = TransactionResponseErrorCodes.valueOf("ERROR_CODE_" + fieldErrorCode).getErrorMessage();
        TransactionResponseErrorModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseErrorModel.class);
        Map<Integer, String> errorsMap = new HashMap<>();
        for (TransactionErrorElement error : response.getErrors()) {
            errorsMap.put(error.getCode(), error.getMessage());
        }

        assertThat("Incorrect error code", response.getCode(), is(errorCode));
        assertThat("Incorrect error message", response.getMessage(), is(errorMessage));
        assertThat("Incorrect field name", response.getErrors()[0].getField(), is(fieldPath.replaceAll("\\[[0-9]]", "")));
        assertThat("Field error code not found in the response", errorsMap.keySet(), hasItem(fieldErrorCode));
        assertThat("Incorrect field error message", errorsMap.get(fieldErrorCode), is(fieldErrorMessage));
    }

    @Then("the {string} response contains error codes for all invalid fields")
    public void validateMultipleResponseErrorCodesAndErrorMessages(String transactionType) {
        Map<String, String> requestDataTable;
        if (transactionType.equalsIgnoreCase("debit")
                || transactionType.equalsIgnoreCase("preauth")
                || transactionType.equalsIgnoreCase("pre-auth")) {
            requestDataTable = Serenity.sessionVariableCalled("debitPreauthRequestDataTable");
        } else if (transactionType.equalsIgnoreCase("cancel")
                || transactionType.equalsIgnoreCase("capture")
                || transactionType.equalsIgnoreCase("refund")) {
            requestDataTable = Serenity.sessionVariableCalled("cancelCaptureRefundRequestDataTable");
        } else {
            throw new IllegalArgumentException("Incorrect transaction type. Supported values: debit, pre-auth/preauth, cancel, capture and refund");
        }
        int errorCode = 12000;
        String errorMessage = TransactionResponseErrorCodes.valueOf("ERROR_CODE_" + errorCode).getErrorMessage();
        TransactionResponseErrorModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseErrorModel.class);

        assertThat("Incorrect error code", response.getCode(), is(errorCode));
        assertThat("Incorrect error message", response.getMessage(), is(errorMessage));

        Map<Integer, String> errorMessagesMap = new HashMap<>();
        Map<Integer, String> errorFieldsMap = new HashMap<>();
        for (TransactionErrorElement error : response.getErrors()) {
            errorMessagesMap.put(error.getCode(), error.getMessage());
            errorFieldsMap.put(error.getCode(), error.getField());
        }

        for (String fieldPath : requestDataTable.keySet()) {
            int fieldErrorCode = 0;
            String field = fieldPath.replaceAll("\\[[0-9]]", "");
            enumLoop:
            for (TransactionResponseErrorCodes enumValue : TransactionResponseErrorCodes.values()) {
                if (enumValue.getFieldPath().equals(field)) {
                    if (enumValue.getErrorMessage().startsWith("Field ")) {
                        if (requestDataTable.get(fieldPath).equals("{NULL}")) {
                            fieldErrorCode = enumValue.getErrorCode();
                            break enumLoop;
                        }
                    }
                    if (enumValue.getErrorMessage().startsWith("Bad value for ")) {
                        if (!requestDataTable.get(fieldPath).equals("{NULL}")) {
                            fieldErrorCode = enumValue.getErrorCode();
                            break enumLoop;
                        }
                    }
                }
            }
            String fieldErrorMessage = TransactionResponseErrorCodes.valueOf("ERROR_CODE_" + fieldErrorCode).getErrorMessage();

            assertThat("Incorrect field name", errorFieldsMap.values(), hasItem(field));
            assertThat("Incorrect field error code", errorMessagesMap.keySet(), hasItem(fieldErrorCode));
            assertThat("Incorrect field error message", errorMessagesMap.values(), hasItem(fieldErrorMessage));
        }
    }

    @Then("the response contains the same values sent in the transaction request")
    public void responseContainsSameValuesSentInTheTransactionRequest() {
        DebitPreauthRequestModel request = Serenity.sessionVariableCalled("generatedJsonObject");
        TransactionResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseModel.class);
        Address billingAddress = response.getBillingAddress();
        Address shippingAddress = response.getShippingAddress();
        Tracking[][] responseTrackingList = new Tracking[response.getTransactions().length][];

        for (int i = 0; i < response.getTransactions().length; i++) {
            responseTrackingList[i] = response.getTransactions()[i].getTracking();
        }

        //set the response fields that are not found in the request to null
        response.setAllowedProducts(null);
        response.setBillingAddress(null);
        response.setCanceledAmount(null);
        response.setCapturedAmount(null);
        response.setCreatedAt(null);
        response.setLastOpereation(null);
        response.setMeta(null);
        response.setModifiedAt(null);
        response.setOrderId(null);
        response.setPaymentInstrument(null);
        response.setPaymentProviderTransactionId(null);
        response.setPreauthorizedAmount(null);
        response.setRedirectUrl(null);
        response.setRefundedAmount(null);
        response.setSettled(null);
        response.setSettlementDate(null);
        response.setShippingAddress(null);
        response.setStatus(null);
        response.setTransactions(null);
        response.setTransactionType(null);
        response.setPrivacy(null);
        response.setSource(null);
        response.setTerms(null);

        //build a validation model with the request values that are found in the response
        TransactionResponseModel validationModel = new TransactionResponseModel();
        validationModel.setBasket((Basket[]) request.getBasket());
        validationModel.setChannel((ChannelType) request.getChannel());
        validationModel.setCurrency(Currency.valueOf(request.getCurrency().toString()));
        validationModel.setCustomer((Customer) request.getCustomer());
        validationModel.setDescription((String) request.getDescription());
        validationModel.setDeviceIdent((DeviceIdent) request.getDeviceIdent());
        validationModel.setInitialAmount((Long) request.getInitialAmount());
        validationModel.setIpAddress((String) request.getIpAddress());
        validationModel.setMerchantOrderId((String) request.getMerchantOrderId());
        validationModel.setPersona((Persona) request.getPersona());
        validationModel.setPrivacy((Long) request.getPrivacy());
        validationModel.setProduct((ProductType) request.getProduct());
        validationModel.setSource((String) request.getSource());
        validationModel.setStatementDescription((String) request.getStatementDescription());
        validationModel.setTerms((Long) request.getTerms());

        //TODO: validate billingAddress and shippingAddress (and tracking, if possible) once the development is done

        assertThat("Request values do not match the values from the response",
                response, equalTo(validationModel));
    }

    @Given("I construct an authentication header using the following values:")
    public void iConstructAuthenticationHeaderBasedOnInput(DataTable dataTable) {
        Map<String, String> originalMap = dataTable.asMap(String.class, String.class);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(originalMap);
        String base64value = Conversions.encodeStringToBase64(map.get("peMerchantId") + ":" + map.get("apiKey"));
        String authHeader = "Basic " + base64value;
        connectionState.setToken(authHeader);
    }

    private void sendTransactionRequest(String transactionType, Object json, boolean hasAuth,
                                        String orderId, String transactionId) throws Exception {
        String endpoint;
        if (Objects.isNull(orderId)) {
            orderId = Serenity.sessionVariableCalled("orderId");
        }
        if (Objects.isNull(transactionId)) {
            transactionId = Serenity.sessionVariableCalled("transactionId");
        }
        switch (transactionType.toLowerCase()) {
            case "debit":
                endpoint = ENDPOINT_DEBIT_TRANSACTION;
                DebitPreauthRequestModel debitJson;
                if (Objects.isNull(json)) {
                    debitJson = JsonObjectGenerator.generateJsonObject(DebitPreauthRequestModel.class);
                } else {
                    debitJson = (DebitPreauthRequestModel) json;
                }
                connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(debitJson), hasAuth);
                TransactionResponseModel debitResponse = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseModel.class);
                if (resultState.getStatusCode() == 200) {
                    Serenity.setSessionVariable("orderId").to(debitResponse.getOrderId());
                    Serenity.setSessionVariable("transactionId").to(debitResponse.getMeta().getThreeDsData().getAuthenticationResult().getTransactionId());
                }
                break;
            case "pre-auth":
            case "preauth":
                endpoint = ENDPOINT_PREAUTH_TRANSACTION;
                DebitPreauthRequestModel preauthJson;
                if (Objects.isNull(json)) {
                    preauthJson = JsonObjectGenerator.generateJsonObject(DebitPreauthRequestModel.class);
                } else {
                    preauthJson = (DebitPreauthRequestModel) json;
                }
                connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(preauthJson), hasAuth);
                TransactionResponseModel preauthResponse = connectionService.convertJsonToModel(resultState.getResult(), TransactionResponseModel.class);
                if (resultState.getStatusCode() == 200) {
                    Serenity.setSessionVariable("orderId").to(preauthResponse.getOrderId());
                    Serenity.setSessionVariable("transactionId").to(preauthResponse.getMeta().getThreeDsData().getAuthenticationResult().getTransactionId());
                }
                break;
            case "cancel":
                endpoint = ENDPOINT_CANCEL_TRANSACTION.replace("{orderId}", orderId).replace("{transactionId}", transactionId);
                CancelCaptureRefundRequestModel cancelJson;
                if (Objects.isNull(json)) {
                    cancelJson = JsonObjectGenerator.generateJsonObject(CancelCaptureRefundRequestModel.class);
                } else {
                    cancelJson = (CancelCaptureRefundRequestModel) json;
                }
                connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(cancelJson), hasAuth);
                break;

            case "capture":
                endpoint = ENDPOINT_CAPTURE_TRANSACTION.replace("{orderId}", orderId).replace("{transactionId}", transactionId);
                CancelCaptureRefundRequestModel captureJson;
                if (Objects.isNull(json)) {
                    captureJson = JsonObjectGenerator.generateJsonObject(CancelCaptureRefundRequestModel.class);
                } else {
                    captureJson = (CancelCaptureRefundRequestModel) json;
                }
                connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(captureJson), hasAuth);
                break;

            case "refund":
                endpoint = ENDPOINT_REFUND_TRANSACTION.replace("{orderId}", orderId).replace("{transactionId}", transactionId);
                CancelCaptureRefundRequestModel refundJson;
                if (Objects.isNull(json)) {
                    refundJson = JsonObjectGenerator.generateJsonObject(CancelCaptureRefundRequestModel.class);
                } else {
                    refundJson = (CancelCaptureRefundRequestModel) json;
                }
                connectionService.getResponseForPOST(endpoint, connectionService.convertToJson(refundJson), hasAuth);
                break;

            default:
                throw new IllegalArgumentException("Incorrect transaction type. Supported values: debit, pre-auth/preauth, cancel and capture");
        }
    }


}
