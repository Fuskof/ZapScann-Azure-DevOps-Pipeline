package smart.hub.steps.api.portal;

import com.microsoft.azure.servicebus.QueueClient;
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
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import redis.clients.jedis.Jedis;
import smart.hub.components.*;
import smart.hub.helpers.Helpers;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.helpers.generators.JsonValueGenerator;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplateEditRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplateSaveRequestModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.RuleTemplatesListResponseModel;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplatesModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.PublishedRuleTemplateResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.RulesTemplateSaveModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.TemplatesModel;
import smart.hub.mappings.serviceBus.models.rules.MerchantRulesModel;
import smart.hub.mappings.serviceBus.models.rules.RulePipelineModel;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static smart.hub.helpers.Constants.*;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class BusinessRulesSteps {

    private final Logger logger = Logger.getLogger(BusinessRulesSteps.class.getName());

    @Autowired
    private Helpers help;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ServiceBus serviceBus;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Environment env;

    @SneakyThrows
    @Given("I send post request to create new business rule template with fields:")
    public void iSendPostRequestToCreateNewBusinessRuleTemplateWithFields(@Transpose RuleTemplateSaveRequestModel json) throws Exception {
        String ruleXML = json.getRuleXml().toString();
        //random id is the last 6 digits from the ruleXML ID
        if (ruleXML.contains("{randomId}")) {
            String newRuleXML = String.valueOf((int) (Math.random() * 1000000 + 1));
            ruleXML = ruleXML.replace("{randomId}", newRuleXML);
            json.setRuleXml(ruleXML);
        }
        connectionService.getResponseForPOST(ENDPOINT_RULES_TEMPLATE_SAVE, connectionService.convertToJson(json));
        Serenity.setSessionVariable("ruleXMLString").to(ruleXML);
        RulesTemplateSaveModel rulesTemplateSaveModel = connectionService
                .convertJsonToModel(resultState.getResult(), RulesTemplateSaveModel.class);
        String stringId = "";
        if (rulesTemplateSaveModel != null) stringId = rulesTemplateSaveModel.getOutput();

        Serenity.setSessionVariable("RuleTemplateId").to(stringId);
        Serenity.setSessionVariable("templateToDelete").to(json.getName());
    }

    @Then("^I send request to view business rule mode$")
    public void iSendRequestToViewTemplateMode() throws Exception {
        String stringId = "";
        if (Serenity.sessionVariableCalled("ruleId") != null)
            stringId = Serenity.sessionVariableCalled("ruleId").toString();

        connectionService.getResponseForGET(ENDPOINT_GET_BUSINESS_RULE_BY_ID + stringId);
    }

    @SneakyThrows
    @Given("I send request to change rule template with fields:")
    public void iSendPutRequestToEditRuleTemplate(@Transpose RuleTemplateEditRequestModel json) throws Exception {
        String ruleTemlpateId = Serenity.sessionVariableCalled("RuleTemplateId");
        json.setId(ruleTemlpateId);
        String ruleXml = Serenity.sessionVariableCalled("ruleXMLString");
        json.setRuleXml(ruleXml);
        connectionService.getResponseForPUT(ENDPOINT_RULES_TEMPLATE_EDIT, json);
        Serenity.setSessionVariable("templateToDelete").to(json.getName());
    }

    @Then("^response contains rule id$")
    public void responseContainsId() throws Exception {
        String stringId = Serenity.sessionVariableCalled("ruleId");
        assertThat(connectionService.convertToJson(resultState.getResult()).contains(stringId)).isTrue();
    }


    @When("^I send request to find published rule template$")
    public void iSendRequestToFindPublishedRuleTemplate() throws Exception {
        String stringSearch = Serenity.sessionVariableCalled("RuleTemplateId");
        connectionService.getResponseForGET(ENDPOINT_PUBLISHED_RULE_TEMPLATE_BY_ID + stringSearch);
        PublishedRuleTemplateResponseModel publishedRuleTemplateResponse = connectionService
                .convertJsonToModel(resultState.getResult(), PublishedRuleTemplateResponseModel.class);
        List<RuleTemplatesModel> ruleTemplates = publishedRuleTemplateResponse.getRuleTemplates();
        String idOfPublishedRuleTemplate = "";
        if (ruleTemplates.size() > 0)
            idOfPublishedRuleTemplate = ruleTemplates.get(0).getId();
        Serenity.setSessionVariable("idOfPublishedRuleTemplate").to(idOfPublishedRuleTemplate);
    }

    @When("^the response contains literal '(.*)'$")
    public void theResponseContainsLiteral(String stringForSearch) {
        HashMap<String, String> map = new HashMap<>();
        map = Serenity.sessionVariableCalled("previousRequestMap");

        PublishedRuleTemplateResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), PublishedRuleTemplateResponseModel.class);
        List<RuleTemplatesModel> listRuleTemplatesModel = response.getRuleTemplates();

        boolean flag = true;
        for (RuleTemplatesModel elementOfList : listRuleTemplatesModel)
            if (!elementOfList.getName().equals(map.get("Name")))
                flag = flag & false;
        assertThat(flag).isTrue();
        assertThat(response.isSuccess()).isTrue();
    }

    @Then("^I send get request for receive list of all rule templates$")
    public void iSendGetRequestForReceiveListOfAllRuleTemplates() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_RULES_TEMPLATE_GET_ALL);
        Serenity.setSessionVariable("previousRequestMap").to(resultState);
    }

    @When("^I send '(.*)' request to publish rule template$")
    public void iSendPutRequestToPublish(String putRequest) throws Exception {

        String[] arrayId = new String[1];
        iSendGetRequestForReceiveListOfAllRuleTemplates();
        RuleTemplatesListResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> list = response.getTemplates();
        if (putRequest.equals("valid")) {
            arrayId[0] = Serenity.sessionVariableCalled("RuleTemplateId");
        } else if (putRequest.equals("empty")) arrayId[0] = "";
        else if (putRequest.equals("wrong")) arrayId[0] = "ZAQ__!@#";

        connectionService.getResponseForPATCH(ENDPOINT_PUBLISH_RULES_TEMPLATES, arrayId);
    }

    @When("I send get request for receive list of rule template:")
    public void iSendGetRequestForReceiveListOfRuleTemplate(Map<String, String> dataTable) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.putAll(dataTable);
        if (map.get("page").equals("X")) { //if I receive 'X' - I will get last page
            RuleTemplatesListResponseModel response = connectionService
                    .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
            int totalItems = Integer.valueOf(response.getTotalItems());
            int pageSize = Integer.valueOf(dataTable.get("pageSize"));
            int inc = 0;
            if (totalItems % pageSize > 0) inc = 1;
            int lastPage = totalItems / pageSize + inc;
            map.put("page", lastPage + "");
        }
        String request = "?";
        for (Map.Entry<String, String> entry : map.entrySet())
            request = request + entry.getKey() + "=" + entry.getValue() + "&";
        request = request.replaceAll(".$", ""); //remove the last symbol &
        connectionService.getResponseForGET(ENDPOINT_RULES_TEMPLATE_GET_ALL + request);
    }

    @Then("^the number of templates on the page is matched to (.*) according to (.*)$")
    public void theNumberOfTemplatesOnThePageIsMatchedToPageSize(String pageSize, String page) throws Exception {
        RuleTemplatesListResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> list = response.getTemplates();
        String estimationS;
        if (page.equals("X")) {
            estimationS = String.valueOf(Integer.valueOf(response.getTotalItems()) % Integer.valueOf(pageSize));
        } else estimationS = String.valueOf(pageSize);
        assertThat(String.valueOf(list.size())).isEqualTo(estimationS);
    }

    @Then("^business rule templates are sorted by (.*) with (.*) order$")
    public void businessRuleAreSortedByName(String sortedBy, String order) throws Exception {
        RuleTemplatesListResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> list = response.getTemplates();
        assertThat(response.isSorted(list, sortedBy, order)).isTrue();
    }

    @Then("^business rule template is persisted in database$")
    public void businessRuleTemplateIsExistedInTheDatabase() throws Exception {
        connectionService.getResponseForGET(ENDPOINT_RULES_TEMPLATE_GET_ALL);
        assertThat(resultState.getResult()).contains(Serenity.sessionVariableCalled("templateToDelete").toString());
    }

    @Then("^business rule status is '(.*)'$")
    public void businessRuleStatus(String expectedStatus) throws Exception {

        connectionService.getResponseForGET(ENDPOINT_RULES_TEMPLATE_GET_ALL);
        RuleTemplatesListResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> listTemplates = response.getTemplates();


        String actualStatus = "";
        for (TemplatesModel elementOfList : listTemplates)
            if (elementOfList.getName().equals(Serenity.sessionVariableCalled("templateToDelete")))
                actualStatus = elementOfList.getStatus();

        assertThat(expectedStatus).isEqualTo(actualStatus);
    }

    @Then("document is existed in the database")
    public void documentIsExistedInTheDatabase() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map = Serenity.sessionVariableCalled("previousRequestMap");

        assertThat(resultState.getResult()).contains(map.get("Name"));
    }

    @Then("test record is removed from the database")
    public void restRecordIsRemovedFromDatabase() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map = Serenity.sessionVariableCalled("previousRequestMap");
        databaseService.executeDelete("RulesConfiguration.RuleTemplates.remove({'Name': '%s'})", map.get("Name"));
        databaseService.executeDelete("RulesConfiguration.TemplateSets.remove({'PublishedTemplates.Name': '%s'})", map.get("Name"));
    }

    @Then("I check Template Set stored in Redis")
    public void iCheckTemplateSetStoredInRedis() throws Exception {
        String key = "masterTEMPLATE_SET_VERSION_CURRENT";
        HashMap<String, String> map = new HashMap<>();
        map = Serenity.sessionVariableCalled("ruleTemplateMap");
        String rule_id = map.get("RuleTemplateId");
        String name = map.get("Name");

        Jedis redisConnection = redisService.getRedisConnection();

        Map<String, String> redisEntry = redisService.getRedisMap(redisConnection, key);
        String templateSets = redisEntry.get("data");
        assertThat(templateSets.contains("rule id=\"" + rule_id + "\""));
        assertThat(templateSets.contains("<name>" + name + "</name>"));

        logger.info("available template sets in Redis are" + templateSets);
    }

    @Given("I select a new Merchant Id and check that it doesn't have any Merchant Rules in Redis")
    public void iSelectNewMerchantIdAndCheckThatItDoesntHaveMerchantRulesInRedis() {
        JsonValueGenerator jsonValueGenerator = new JsonValueGenerator();
        jsonValueGenerator.generateJsonValue("{INT:8s}");
        String merchantId = jsonValueGenerator.getStringValue();
        org.hamcrest.MatcherAssert.assertThat(String.format("Merchant Id: %s already has Merchant Rules in Redis", merchantId),
                redisService.getRedisMap("masterMERCHANT_ID_" + merchantId).size(), is(0));
        Serenity.setSessionVariable("merchantId").to(merchantId);
    }

    @When("I send a random Rule Pipeline message to Service Bus for the test merchant")
    public void iSendARandomRulePipelineMessageToServiceBus() {
        String merchantId = Serenity.sessionVariableCalled("merchantId");
        MerchantRulesModel merchantRules = generateRandomMerchantRuleByMerchantId(merchantId);
        iSendARulePipelineMessageToServiceBus(merchantRules);
    }

    @When("I send a Rule Pipeline message to Service Bus with specific values:")
    public void iSendARandomRulePipelineMessageToServiceBus(@Transpose MerchantRulesModel merchantRules) {
        iSendARulePipelineMessageToServiceBus(merchantRules);
    }

    @Then("the Merchant Rules information is updated in Redis")
    public void theMerchantRulesInformationIsUpdatedInRedis() {
        String merchantId = Serenity.sessionVariableCalled("merchantId");
        MerchantRulesModel savedMerchantRules = Serenity.sessionVariableCalled("savedMerchantRules");
        String merchantSet = (String) savedMerchantRules.getMerchantSet();
        String expectedMerchantSet = new String(Base64.getDecoder().decode(merchantSet), StandardCharsets.UTF_8);
        String actualMerchantSet = redisService.getRedisValueFromMap("masterMERCHANT_ID_" + merchantId, "data");
        org.hamcrest.MatcherAssert.assertThat("Merchant rules not updated correctly in Redis",
                actualMerchantSet, equalTo(expectedMerchantSet));
    }

    @Then("no Merchant Rules information is saved in Redis")
    public void noMerchantRulesInformationIsSavedInRedis() {
        String merchantId = Serenity.sessionVariableCalled("merchantId");
        org.hamcrest.MatcherAssert.assertThat(String.format("Merchant Id: %s has Merchant Rules in Redis", merchantId),
                redisService.getRedisMap("masterMERCHANT_ID_" + merchantId).size(), is(0));
    }

    private void iSendARulePipelineMessageToServiceBus(MerchantRulesModel merchantRules) {
        QueueClient queueClient =
                serviceBus.getServiceBusQueueClient(env.getProperty("serviceBus.queue.rulePipeline"));
        String merchantRulesJson = connectionService.convertToJson(merchantRules);
        serviceBus.sendServiceBusMessageAsJson(queueClient, merchantRulesJson);
        Serenity.setSessionVariable("savedMerchantRules").to(merchantRules);
    }

    private MerchantRulesModel generateRandomMerchantRuleByMerchantId(String merchantId) {
        if (Objects.isNull(merchantId)) {
            JsonValueGenerator jsonValueGenerator = new JsonValueGenerator();
            jsonValueGenerator.generateJsonValue("{INT:8s}");
            merchantId = jsonValueGenerator.getStringValue();
        }
        HashMap<String, String> inputMap = new HashMap<>();
        inputMap.put("rules.merchantId", merchantId);
        RulePipelineModel rulePipeline = JsonObjectGenerator.generateJsonObject(RulePipelineModel.class, inputMap);
        String rulePipelineJson = connectionService.convertToJson(rulePipeline);
        String rulePipelineJsonBase64 = Base64.getEncoder().encodeToString(rulePipelineJson.getBytes());
        MerchantRulesModel merchantRules = new MerchantRulesModel();
        merchantRules.setPipelineKey(merchantId);
        merchantRules.setMerchantSet(rulePipelineJsonBase64);
        return merchantRules;
    }
}