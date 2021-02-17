package smart.hub.steps.api.portal;

import configuration.AppConfiguration;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.ResultState;
import smart.hub.helpers.Helpers;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.helpers.generators.JsonValueGenerator;
import smart.hub.mappings.api.enums.proxyAPI.transaction.SourceKeyEnum;
import smart.hub.mappings.api.models.request.adminAPI.RuleConfigurationSaveRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.integrationManager.UpdateConfigurationsRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.merchants.MerchantBodyModel;
import smart.hub.mappings.api.models.request.adminAPI.merchants.OnBoardMerchantModel;
import smart.hub.mappings.api.models.response.adminAPI.RulesConfigurationGetResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.UpdateConfigurationsResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.integrationManager.GetConfigurationsResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.merchants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static smart.hub.helpers.Constants.*;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class AdminApiSteps {

    private final Logger logger = Logger.getLogger(AdminApiSteps.class.getName());

    @Autowired
    private Helpers help;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @Given("I retrieve all the merchants for '(.*)'$")
    public void iRetrieveAllTheMerchantsFor(String endPoint) throws Exception {
        connectionService.getResponseForGET(ENDPOINT_GET_ALL_MERCHANTS + endPoint);
    }

    @Given("^I send request to get external merchants$")
    public void iSendGetRequestToGetExternalMerchants() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_GET_ALL_EXTERNAL_MERCHANTS);
        GetAllExternalMerchantsResponseModel[] getAllMerchantsResponseArray = connectionService
                .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class);
        Serenity.setSessionVariable("getAllMerchantsResponseArray").to(getAllMerchantsResponseArray);
        Serenity.setSessionVariable("merchantId").to(getAllMerchantsResponseArray[0].getExternalId());
    }

    @SneakyThrows
    @When("^I send request to change type of merchant with status '(.*)'$")
    public void iSendRequestToChangeMerchantStatus(String status, @Transpose MerchantBodyModel json) throws Exception {
        GetAllExternalMerchantsResponseModel[] getAllExternalMerchantsResponseModels =
                Serenity.sessionVariableCalled("getAllMerchantsResponseArray");
        GetAllExternalMerchantsResponseModel[] response = null;
        for (int i = 0; i < getAllExternalMerchantsResponseModels.length; i++) {
            connectionService.getResponseForGET(ENDPOINT_GET_ALL_EXTERNAL_MERCHANTS + "?merchantId=" + getAllExternalMerchantsResponseModels[i].getExternalId());
            response = connectionService
                    .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class);
            for (GetAllExternalMerchantsResponseModel merchant : response) {
                if (merchant.getStatus().contains(status)) {

                    connectionService.getResponseForPATCH(ENDPOINT_ENRICH_ONBOARDING_WITH_ENTITY_TYPE +
                            merchant.getId(), json);
                    i = getAllExternalMerchantsResponseModels.length;
                    if (resultState.getStatusCode() == 200) Serenity.setSessionVariable("response").to(connectionService
                            .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class));
                    break;
                }
            }
        }
    }

    @Given("^I save all merchants with status '(.*)' and hasChildren set to '(.*)'$")
    public void iFindMerchantWithStatus(String statusOfMerchant, boolean hasChildren) throws Exception {
        HashMap<String, String> merchantIdsMap = new HashMap<>();

        GetAllExternalMerchantsResponseModel[] getAllExternalMerchantsResponseModels =
                Serenity.sessionVariableCalled("getAllMerchantsResponseArray");
        for (int i = 0; i < getAllExternalMerchantsResponseModels.length; i++) {
            if (getAllExternalMerchantsResponseModels[i].getStatus().contains(statusOfMerchant)
                    && getAllExternalMerchantsResponseModels[i].isHasChildren() == hasChildren) {
                //making  the key to be external ID ---> Value is MerchantID
                merchantIdsMap.put(getAllExternalMerchantsResponseModels[i].getExternalId(), getAllExternalMerchantsResponseModels[i].getId());
            }
        }
        Serenity.setSessionVariable("listContainingMapOfMerchantIds").to(merchantIdsMap);
    }

    @When("^I send get request with '(.*)' value to check Dictionary Service$")
    public void iSendGetRequestToCheckDictionaryService(SourceKeyEnum sourceKey) throws Exception {
        String request = "";
        List<Integer> activeCodesList = new ArrayList();
        List<Boolean> bool = new ArrayList<>();
        GetValuesByComponentSourceKeyResponseModel getValuesByComponent = new GetValuesByComponentSourceKeyResponseModel();
        GetAllExternalMerchantsResponseModel[] getAllMerchantsResponseArray = Serenity
                .sessionVariableCalled("getAllMerchantsResponseArray");
        for (int i = 0; i < getAllMerchantsResponseArray.length; i++) {
            if (getAllMerchantsResponseArray[i].getId() != null && getAllMerchantsResponseArray[i].isHasChildren()
                    && getAllMerchantsResponseArray[i].getStatus().contains("Active")) {
                request = sourceKey.toString() + "?merchantId=" + getAllMerchantsResponseArray[i].getId();
                connectionService.getResponseForGET(ENDPOINT_GET_VALUES_BY_COMPONENT_SOURCE_KEY + request);
                getValuesByComponent = connectionService
                        .convertJsonToModel(resultState.getResult(), GetValuesByComponentSourceKeyResponseModel.class);
                activeCodesList.add(resultState.getStatusCode());
                if (getValuesByComponent.isHasErrors())
                    bool.add(true);
                else
                    bool.add(false);
            }
        }
        Serenity.setSessionVariable("activeCodesList").to(activeCodesList);
        Serenity.setSessionVariable("listOfBoolean").to(bool);
    }

    @Given("^I send request to get merchants with status '(.*)' and name '(.*)'$")
    public void iSendGetRequestToGetMerchants(String status, String name) throws Exception {
        connectionService.getResponseForGET(ENDPOINT_GET_MERCHANTS + status);
        GetAllActiveMerchantsResponseModel getAllActiveMerchantsResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetAllActiveMerchantsResponseModel.class);
        List<MerchantsResponseModel> merchantsResponseModels = getAllActiveMerchantsResponseModel.getMerchants();
        for (MerchantsResponseModel el : merchantsResponseModels)
            if (el.getName().equals(name))
                Serenity.setSessionVariable("merchantId").to(el.getId());
    }

    @Then("^all responses have '(.*)' values in the hasErrors fields$")
    public void allResponsesHaveTrueValues(boolean estimate) {
        List<Boolean> actual = Serenity.sessionVariableCalled("listOfBoolean");
        for (int i = 0; i < actual.size(); i++) {
            assertThat(estimate).isEqualTo(actual.get(i));
        }
    }

    @Given("^I find and save a child of merchant having status set to '(.*)' and hasChildren set to '(.*)'$")
    public void iSendRequestToFindAllChilds(String status, boolean haschildren) throws Exception {
        Map<String, String> merchants = Serenity.sessionVariableCalled("listContainingMapOfMerchantIds");
        for (Map.Entry<String, String> entry : merchants.entrySet()) {
            //Key represents External ID ----> Value represents Merchant ID
            String externalId = entry.getKey();
            connectionService.getResponseForGET(ENDPOINT_GET_ALL_EXTERNAL_MERCHANTS + "?merchantId=" + externalId);
            GetAllExternalMerchantsResponseModel[] getAllMerchantsResponseArray = connectionService
                    .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class);
            for (int i = 0; i < getAllMerchantsResponseArray.length; i++) {
                if (getAllMerchantsResponseArray[i].getStatus().contains(status) &&
                        getAllMerchantsResponseArray[i].isHasChildren() == haschildren) {
                    externalId = getAllMerchantsResponseArray[i].getExternalId();
                    Serenity.setSessionVariable("externalId").to(externalId);
                    break;
                }
            }
        }
    }

    @When("^I board a child of the merchant$")
    public void iSendPostRequestToBoardingChildOfMerchant() throws Exception {
        String externalId = Serenity.sessionVariableCalled("externalId");

        OnBoardMerchantModel onBoardMerchantModel = new OnBoardMerchantModel();
        onBoardMerchantModel.setMerchantId(externalId);
        connectionService.getResponseForPOST(ENDPOINT_ONBOARD_MERCHANTS, connectionService.convertToJson(onBoardMerchantModel));
        OnBoardResponseForPostModel[] onBoardResponseForPostModel = connectionService
                .convertJsonToModel(resultState.getResult(), OnBoardResponseForPostModel[].class);
        if (onBoardResponseForPostModel != null) {
            externalId = onBoardResponseForPostModel[0].getId();
            Serenity.setSessionVariable("onboardedMerchantId").to(externalId);
        }
    }

    @When("^I assign to the merchant$")
    public void iSendPatchRequestToAssignTypeOfMerchant(Map<String, String> dataTable) throws Exception {
        String onboardedMerchantId = Serenity.sessionVariableCalled("onboardedMerchantId");
        connectionService.getResponseForPATCH(ENDPOINT_ENRICH_ONBOARDING_WITH_ENTITY_TYPE + onboardedMerchantId,
                connectionService.convertToJson(dataTable));
    }

    @Then("^the merchant status is '(.*)' and type is '(.*)'$")
    public void theMerchantsStatusAndType(String status, String type) {
        OnBoardResponseForPostModel[] onBoardResponseForPostModels = connectionService
                .convertJsonToModel(resultState.getResult(), OnBoardResponseForPostModel[].class);
        assertThat(onBoardResponseForPostModels[0].getStatus()).contains(status);
        assertThat(onBoardResponseForPostModels[0].getType()).contains(type);
    }

    @When("^I send post request to boarding merchant$")
    public void iSendPostRequestToBoardingMerchant() throws Exception {
        String onBoardedExternalId = Serenity.sessionVariableCalled("onBoardedExternalId");
        OnBoardMerchantModel onBoardMerchantModel = new OnBoardMerchantModel();
        if (onBoardedExternalId == null) onBoardedExternalId = "abracadabra";
        onBoardMerchantModel.setMerchantId(onBoardedExternalId);
        connectionService.getResponseForPOST(ENDPOINT_ONBOARD_MERCHANTS, connectionService.convertToJson(onBoardMerchantModel));
    }

    @When("^I find a merchant with status '(.*)'$")
    public void iFinfMerchantWithStatus(String statusOfMerchant) throws Exception {
        GetAllExternalMerchantsResponseModel[] getAllMerchantsResponseArray = connectionService
                .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class);
        for (GetAllExternalMerchantsResponseModel el : getAllMerchantsResponseArray)
            if (el.getStatus().contains(statusOfMerchant)) {
                Serenity.setSessionVariable("onBoardedMerchantId").to(el.getExternalId());
                break;
            }
    }

    @Then("^I send put request to assign to merchant status '(.*)'$")
    public void iSendPutRequestToAssignToMerchantStatus(String statusOfMerchant) throws Exception {
        String onBoardedMerchantId = Serenity.sessionVariableCalled("onBoardedMerchantId");
        String request = "?merchantId=" + onBoardedMerchantId + "&status=" + statusOfMerchant;
        connectionService.getResponseForPUT(ENDPOINT_CHANGE_MERCHANT_STATUS + request, "");
    }

    @Then("^the response contains id of boarded merchant$")
    public void responseContainsIdOfBoardedMerchant() {
        String onBoardedExternalId = Serenity.sessionVariableCalled("onBoardedExternalId");
        assertThat(resultState.getRequestJson().contains(onBoardedExternalId)).isTrue();
    }

    @Then("^the body of GetAllExtPortalMerchants response contains '(.*)' and '(.*)'$")
    public void getAllExtMerchantResponseContainsStrings(String id, String name) {
        GetExternalMerchantsResponseModel[] getAllMerchants = connectionService.convertJsonToModel(resultState.getResult(), GetExternalMerchantsResponseModel[].class);

        for (GetExternalMerchantsResponseModel merchant : getAllMerchants) {
            assertThat(merchant).isInstanceOf(GetExternalMerchantsResponseModel.class);
            assertThat(merchant.toString()).contains(id);
            assertThat(merchant.toString()).contains(name);
        }
    }

    @Then("^the type of changed merchant is '(.*)'$")
    public void theTypeOfActiveMerchant(String expectedType) {
        GetAllExternalMerchantsResponseModel[] response = Serenity.sessionVariableCalled("response");
        assertThat(expectedType).isEqualTo(response[0].getType());
    }

    @Then("^the body of GetAllMerchants response contains '(.*)' and '(.*)'$")
    public void getAllMerchantResponseContainsStrings(String id, String name) {
        GetAllMerchantsResponseModel[] getAllMerchants = connectionService.convertJsonToModel(resultState.getResult(), GetAllMerchantsResponseModel[].class);

        for (GetAllMerchantsResponseModel merchant : getAllMerchants) {
            assertThat(merchant).isInstanceOf(GetAllMerchantsResponseModel.class);
            assertThat(merchant.toString()).contains(id);
            assertThat(merchant.toString()).contains(name);
        }
    }

    @Given("I update a Configuration with specific fields:")
    public void iUpdateAConfigurationWithSpecificFields(@Transpose UpdateConfigurationsRequestModel json) throws Exception {
        connectionService.getResponseForPOST(ENDPOINT_UPDATE_INTEGRATION_MANAGER_CONFIGURATIONS, connectionService.convertToJson(json));
    }

    @SneakyThrows
    @Given("I update a Configuration with random fields")
    public void iUpdateAConfigurationWithRandomFields() {
        connectionService.getResponseForPOST(ENDPOINT_UPDATE_INTEGRATION_MANAGER_CONFIGURATIONS, connectionService.convertToJson(JsonObjectGenerator.generateJsonObject(UpdateConfigurationsRequestModel.class)));
        Serenity.setSessionVariable("key").to(help.getNode("key", "configurationItem"));
    }

    @Given("the body of Update Configuration response contains status '(.*)'$")
    public void theBodyOfUpdateConfigurationResponseContainsStatusTrue(boolean isValid) {
        UpdateConfigurationsResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), UpdateConfigurationsResponseModel.class);
        assertThat(response.isStatus()).isEqualTo(isValid);
    }

    @SneakyThrows
    @When("I retrieve a Configuration for a previously saved key")
    public void iRetrieveAConfigurationWithForAPreviouslySavedKey() {
        String key = Serenity.sessionVariableCalled("key");
        connectionService.getResponseForGET(ENDPOINT_GET_INTEGRATION_MANAGER_CONFIGURATIONS + key);
    }

    @Given("the response contains a configuration for that key with the status set to '(.*)'$")
    public void theResponseContainsAKeyWithStatus(boolean isValid) {
        GetConfigurationsResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), GetConfigurationsResponseModel.class);
        assertThat(response.isFound()).isEqualTo(isValid);
    }

    @SneakyThrows
    @Given("I retrieve a Configuration for an unregistered key '(.*)'$")
    public void iRetrieveAConfigurationForAnInvalidKey(String key) {
        key = new JsonValueGenerator().generateJsonValue(key).getStringValue();
        connectionService.getResponseForGET(ENDPOINT_GET_INTEGRATION_MANAGER_CONFIGURATIONS + key);
    }

    @SneakyThrows
    @Given("I save a rule with specific fields:")
    public void iSaveARuleWithSpecificFields(@Transpose RuleConfigurationSaveRequestModel json) {
        HashMap<String, String> savedRule = new HashMap<>();

        connectionService.getResponseForPOST(ENDPOINT_RULES_CONFIGURATION_SAVE, connectionService.convertToJson(json));

        savedRule.put("merchantId", help.getNode("merchantId"));
        savedRule.put("globalId", help.getNode("globalId"));
        savedRule.put("ruleId", help.getNode("ruleId"));

        Serenity.setSessionVariable("previousRequestMap").to(savedRule);
    }

    @SneakyThrows
    @Given("I update the Rule of the previously saved merchant Id")
    public void iUpdateThePreviousCreatedRuleForMerchantId(@Transpose RuleConfigurationSaveRequestModel json) {
        HashMap<String, String> previousRequestMap = Serenity.sessionVariableCalled("previousRequestMap");

        String jsonString = connectionService.convertToJson(json);
        resultState.setRequestJson(jsonString);
        jsonString = help.modifySingleNodeJson("merchantId", previousRequestMap.get("merchantId"));

        connectionService.getResponseForPOST(ENDPOINT_RULES_CONFIGURATION_SAVE, jsonString);
    }

    @SneakyThrows
    @Given("I update the Rule of the previously saved global Id")
    public void iUpdateThePreviousCreatedRuleForGlobalId(@Transpose RuleConfigurationSaveRequestModel json) {
        HashMap<String, String> previousRequestMap = Serenity.sessionVariableCalled("previousRequestMap");

        String jsonString = connectionService.convertToJson(json);
        resultState.setRequestJson(jsonString);
        jsonString = help.modifySingleNodeJson("globalId", previousRequestMap.get("globalId"));

        connectionService.getResponseForPOST(ENDPOINT_RULES_CONFIGURATION_SAVE, jsonString);
    }

    @SneakyThrows
    @When("I retrieve the rule for the previously used '(.*)'$")
    public void iRetrieveTheRuleIdForThePreviouslyUsedId(String id) {
        HashMap<String, String> previousRequestMap = Serenity.sessionVariableCalled("previousRequestMap");

        if (id.equals("merchantId")) {
            id = previousRequestMap.get("merchantId");
        } else {
            id = previousRequestMap.get("globalId");
        }

        connectionService.getResponseForGET(ENDPOINT_RULES_CONFIGURATION_GET + id);
    }

    @Given("the response contains the previously used '(.*)'$")
    public void theResponseContainsThePreviouslyUsedMerchantId(String id) {
        HashMap<String, String> previousRequestMap = Serenity.sessionVariableCalled("previousRequestMap");
        RulesConfigurationGetResponseModel response = connectionService.convertJsonToModel(resultState.getResult(), RulesConfigurationGetResponseModel.class);

        if (id.equals("merchantId")) {
            assertThat(response.getMerchantId()).contains(previousRequestMap.get(id));
        } else {
            assertThat(response.getGlobalId()).contains(previousRequestMap.get(id));
        }
    }

    @SneakyThrows
    @When("I publish the rule")
    public void iPublishTheRule() {
        HashMap<String, String> previousRequestMap = Serenity.sessionVariableCalled("previousRequestMap");
        connectionService.getResponseForPOST(ENDPOINT_RULES_CONFIGURATION_PUBLISH + previousRequestMap.get("ruleId"));
    }
}

