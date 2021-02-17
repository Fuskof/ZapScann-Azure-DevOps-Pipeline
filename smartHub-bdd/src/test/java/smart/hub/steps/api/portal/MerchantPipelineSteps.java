package smart.hub.steps.api.portal;

import configuration.AppConfiguration;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import smart.hub.components.ConnectionService;
import smart.hub.components.DatabaseService;
import smart.hub.components.ResultState;
import smart.hub.helpers.Helpers;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.AddRuleToPipelineRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.ModifyBusinessRuleRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.PublishPipelineRequestModel;
import smart.hub.mappings.api.models.response.adminAPI.pipelines.AddRuleToPipelineResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.merchants.*;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.RulesResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.RuleTemplatesListResponseModel;
import smart.hub.mappings.api.models.response.adminAPI.pipelines.*;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.TemplatesModel;
import smart.hub.mappings.api.models.response.adminAPI.ruleTemplates.publishedRuleTemplateBySearch.PublishedRuleTemplateAttributesModel;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static smart.hub.helpers.Constants.*;

@Configurable
@ContextConfiguration(classes = AppConfiguration.class)
public class MerchantPipelineSteps {

    private final Logger logger = Logger.getLogger(MerchantPipelineSteps.class.getName());

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private Helpers help;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ResultState resultState;

    @When("^I search for rule template  by criteria text (.*) and type (.*) and status (.*)$")
    public void iLSearchRuleTemplateWithText(String textToSearch, String type, String status) throws Exception {
        if (textToSearch.contains("Empty")) textToSearch = "";
        if (type.contains("Empty")) type = "";
        if (status.contains("Empty")) status = "";

        String endpoint = ENDPOINT_SEARCH_RULE_TEMPLATE_BY_CRITERIA.replace("{text}", textToSearch)
                .replace("{type}", type)
                .replace("{status}", status);
        connectionService.getResponseForGET(endpoint);
    }

    @SneakyThrows
    @When("^I send request to create new rule with fields:$")
    public void iSendPostRequestToAddRule(@Transpose AddRuleToPipelineRequestModel json) throws Exception {
        if (json.getRuleTemplateId().toString().contains("reassign-from-previous-request")) {
            json.setRuleTemplateId(Serenity.sessionVariableCalled("RuleTemplateId"));
            json.setMerchantId(Serenity.sessionVariableCalled("merchantId").toString());
        }
        connectionService.getResponseForPOST(ENDPOINT_ADD_RULE_TO_PIPELINE, connectionService.convertToJson(json));
        Serenity.setSessionVariable("ruleName").to(json.getRuleName());
        Serenity.setSessionVariable("merchantId").to(json.getMerchantId());
        Serenity.setSessionVariable("pipelineLevel").to(json.getLevel());
    }

    @SneakyThrows
    @When("^I send request to change business rule with fields:$")
    public void iSendPostRequestToEditRule(@Transpose ModifyBusinessRuleRequestModel json) throws Exception {
        if (!json.getRuleId().toString().equals("WrongValue"))
            json.setRuleId(Serenity.sessionVariableCalled("ruleId"));
        connectionService.getResponseForPUT(ENDPOINT_MODIFY_A_BUSINESS_RULE, json);
        Serenity.setSessionVariable("ruleName").to(json.getRuleName());
        Serenity.setSessionVariable("pipelineLevel").to(json.getLevel());
    }

    @When("^I find id of new business rule")
    public void iFindIdOfNewRule() {
        String nameOfRule = Serenity.sessionVariableCalled("ruleName");
        GetPipelineResponseModel pipelineResponseModel = Serenity.sessionVariableCalled("pipelineResponse");
        List<PipeLinesModel> pipeLinesModel = pipelineResponseModel.getPipelines();
        for (PipeLinesModel pipeline : pipeLinesModel) {
            List<RulesResponseModel> rulesResponseModels = pipeline.getRules();
            for (RulesResponseModel rule : rulesResponseModels) {
                if (rule.getName().equals(nameOfRule))
                    Serenity.setSessionVariable("ruleId").to(rule.getId());
            }
        }
    }

    @When("^I request to get pipeline with fields:$")
    public void iSendGetRequestToGetPipeline(Map<String, String> dataTable) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(dataTable);
        if (map.get("level").contains("Individual")) {
            map.put("merchantId", Serenity.sessionVariableCalled("merchantId"));
        }
        if (map.get("merchantId") == null) {
            connectionService.getResponseForGET(ENDPOINT_GET_ALL_EXTERNAL_MERCHANTS);
            GetAllExternalMerchantsResponseModel[] getAllMerchantsResponseArray = connectionService
                    .convertJsonToModel(resultState.getResult(), GetAllExternalMerchantsResponseModel[].class);
            map.put("merchantId", getAllMerchantsResponseArray[0].getId());
        }
        map.put("merchantId", Serenity.sessionVariableCalled("merchantId"));
        String request = "/";
        for (Map.Entry<String, String> entry : map.entrySet())
            if (entry.getValue() != null)
                request = request + entry.getValue() + "/";

        request = request.replaceAll(".$", ""); //remove the last symbol &
        connectionService.getResponseForGET(ENDPOINT_API_RULE_GET_PIPELINES + request);

        GetPipelineResponseModel getPipelineResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);
        Serenity.setSessionVariable("pipelineResponse").to(getPipelineResponseModel);
        List<PipeLinesModel> pipelines;
        if (getPipelineResponseModel != null) {
            pipelines = getPipelineResponseModel.getPipelines();
            String ruleId = "";
            if (pipelines != null)
                for (int i = 0; i < pipelines.size(); i++)
                    if (pipelines.get(i).isEnabled() && pipelines.get(i).getRules().size() > 0) {
                        ruleId = pipelines.get(i).getRules().get(0).getId();
                        Serenity.setSessionVariable("ruleId").to(ruleId);
                        break;
                    }
            Serenity.setSessionVariable("pipelines").to(pipelines);
        }
        Serenity.setSessionVariable("previousRequestMap").to(map);
    }

    @When("^I send request with tag '(.*)' to receive dictionary of country$")
    public void iSendGetRequestToReceiveDictionary(String sourceKey) throws Exception {
        connectionService.getResponseForGET(ENDPOINT_GET_VALUES_BY_COMPONENT_SOURCE_KEY + sourceKey);
    }

    @When("^I send request to add new rule with fields:$")
    public void iSendPostRequestToAddRule(Map<String, String> dataTable) throws Exception {
        List<AttributesOfRuleResponseModel> attributesOfRuleModelList = new ArrayList<>();

        String[] arr = dataTable.get("arrayOfValues1").split(",");
        AttributesOfRuleResponseModel attributesOfRuleModel = AttributesOfRuleResponseModel.builder()
                .key(dataTable.get("key1"))
                .description(dataTable.get("description1"))
                .value(dataTable.get("value1"))
                .uiControl(dataTable.get("uiControl1"))
                .valueKind(dataTable.get("valueKind1"))
                .sourceKey(dataTable.get("sourceKey1"))
                .arrayOfValues(arr)
                .build();
        attributesOfRuleModelList.add(attributesOfRuleModel);
        arr = dataTable.get("arrayOfValues2").split(",");
        attributesOfRuleModel = AttributesOfRuleResponseModel.builder()
                .key(dataTable.get("key2"))
                .description(dataTable.get("description2"))
                .value(dataTable.get("value2"))
                .uiControl(dataTable.get("uiControl2"))
                .valueKind(dataTable.get("valueKind2"))
                .sourceKey(dataTable.get("sourceKey2"))
                .arrayOfValues(arr)
                .build();
        attributesOfRuleModelList.add(attributesOfRuleModel);
        connectionService.getResponseForGET(ENDPOINT_RULES_TEMPLATE_GET_ALL);
        RuleTemplatesListResponseModel response = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> templatesModelList = response.getTemplates();
        TemplatesModel myTemplate = templatesModelList.get(templatesModelList.size() - 1);
        String ruleTemplateId = "";
        if (dataTable.get("ruleTemplateId").contains("")) ruleTemplateId = myTemplate.getId();
        AddRuleToPipelineResponseModel addRuleToPipelineModel = new AddRuleToPipelineResponseModel();
        if (dataTable.get("ruleName").contains("random"))
            addRuleToPipelineModel.setRuleName("QA_test_" + (int) (Math.random() * 1000 + 1));
        else
            addRuleToPipelineModel.setRuleName(dataTable.get("ruleName"));
        addRuleToPipelineModel.setLevel(dataTable.get("level"));
        addRuleToPipelineModel.setMerchantId(dataTable.get("merchantId"));
        addRuleToPipelineModel.setRuleTemplateId(dataTable.get("ruleTemplateId"));
        if (Serenity.sessionVariableCalled("idOfActiveMerchant") != null)
            addRuleToPipelineModel.setMerchantId(Serenity.sessionVariableCalled("idOfActiveMerchant"));
        if (Serenity.sessionVariableCalled("idOfPublishedRuleTemplate") != null)
            addRuleToPipelineModel.setRuleTemplateId(Serenity.sessionVariableCalled("idOfPublishedRuleTemplate"));
        addRuleToPipelineModel.setAttributes(attributesOfRuleModelList);
        connectionService.getResponseForPOST(ENDPOINT_ADD_RULE_TO_PIPELINE, connectionService.convertToJson(addRuleToPipelineModel));
        Serenity.setSessionVariable("ruleName").to(addRuleToPipelineModel.getRuleName());
        Serenity.setSessionVariable("merchantId").to(dataTable.get("merchantId"));
    }

    @Then("^new rule (.*) present in the pipeline$")
    public void pipeLineContainsNewRule(String isPresent) throws Exception {
        boolean isPresentBoolean = true;
        if (isPresent.equals("isn\'t"))
            isPresentBoolean = false;
        String merchantId = Serenity.sessionVariableCalled("merchantId");
        String ruleName = Serenity.sessionVariableCalled("ruleName");
        String pipelineLevel = Serenity.sessionVariableCalled("pipelineLevel");

        boolean isContains = false;
        connectionService.getResponseForGET(ENDPOINT_API_RULE_GET_PIPELINES + "/" + pipelineLevel + "/" + merchantId);

        GetPipelineResponseModel responseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);
        List<PipeLinesModel> pipeLinesModel = responseModel.getPipelines();
        List<RulesResponseModel> rulesResponseModels = new ArrayList<>();
        for (PipeLinesModel pipeLine : pipeLinesModel)
            if (pipeLine.getRules() != null)
                for (RulesResponseModel rule : pipeLine.getRules())
                    if (rule.getName().contains(ruleName))
                        isContains = isContains || true;
        assertThat(isContains).isEqualTo(isPresentBoolean);
    }

    @Then("^text '(.*)', '(.*)' and '(.*)' is persisted in every template of response$")
    public void textIsPersistedInTheResponse(String textToSearch, String type, String status) {
        if (textToSearch.contains("Empty")) textToSearch = "";
        if (type.contains("Empty")) type = "";
        if (status.contains("Empty")) status = "";
        RuleTemplatesListResponseModel ruleTemplatesListResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), RuleTemplatesListResponseModel.class);
        List<TemplatesModel> templatesModel = ruleTemplatesListResponseModel.getTemplates();
        if (templatesModel != null)
            for (TemplatesModel template : templatesModel) {
                assertThat(template.getName().toLowerCase().contains(textToSearch.toLowerCase())).isTrue();
                if (!status.contains("None")) assertThat(template.getStatus().contains(status)).isTrue();
                if (!type.contains("None")) assertThat(template.getType().contains(type)).isTrue();
            }
    }

    @Then("^response has no errors$")
    public void responseHasNoErrors() {
        GetValuesByComponentSourceKeyResponseModel getValuesByComponentSourceKey = connectionService
                .convertJsonToModel(resultState.getResult(), GetValuesByComponentSourceKeyResponseModel.class);
        assertThat(getValuesByComponentSourceKey.isHasErrors()).isFalse();
        assertThat(getValuesByComponentSourceKey.getErrorCode()).isNull();
    }

    @When("^I send request to add rule with fields:$")
    public void iSendRequestToAddRule(Map<String, String> dataTable) throws Exception {
        List<PublishedRuleTemplateAttributesModel> listOfAttributes = Serenity.sessionVariableCalled("listIOfAttributes");
        PublishedRuleTemplateAttributesModel publishedRuleTemplateAttributesModel = new PublishedRuleTemplateAttributesModel();
        if (listOfAttributes != null) {
            publishedRuleTemplateAttributesModel = listOfAttributes.get(0);
            publishedRuleTemplateAttributesModel.setValue(dataTable.get("value"));
            if (listOfAttributes.size() > 1) {
                publishedRuleTemplateAttributesModel = listOfAttributes.get(1);
                publishedRuleTemplateAttributesModel.setArrayOfValues(Serenity.sessionVariableCalled("listIOfActiveMerchants"));
            }
        }
        AddRuleToPipelineResponseModel addRuleToPipelineModel = new AddRuleToPipelineResponseModel();
        addRuleToPipelineModel.setMerchantId(Serenity.sessionVariableCalled("idOfActiveMerchant"));
        addRuleToPipelineModel.setRuleName(dataTable.get("ruleName"));
        addRuleToPipelineModel.setRuleTemplateId(Serenity.sessionVariableCalled("idOfRuleTemplate"));
        addRuleToPipelineModel.setLevel(dataTable.get("level"));
        addRuleToPipelineModel.setAttributes(Serenity.sessionVariableCalled("listIOfAttributes"));

        connectionService.getResponseForPOST(ENDPOINT_ADD_RULE_TO_PIPELINE, connectionService.convertToJson(addRuleToPipelineModel));
        Serenity.setSessionVariable("ruleName").to(dataTable.get("ruleName"));
    }

    @When("^I send request to publish pipeline$")
    public void iSendPutRequestToPublishPipeline() throws Exception {
        PublishPipelineRequestModel publishPipelineRequestModel = new PublishPipelineRequestModel();
        GetPipelineResponseModel getPipelineResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);
        List<PipeLinesModel> response = getPipelineResponseModel.getPipelines();
        publishPipelineRequestModel.setLevel(response.get(0).getLevel().toString());
        publishPipelineRequestModel.setMerchantId(Serenity.sessionVariableCalled("merchantId"));
        connectionService.getResponseForPUT(ENDPOINT_PUBLISH_PIPELINE, publishPipelineRequestModel);
    }

    @When("^I send put request to update rule$")
    public void iSendPostRequestToUpdateRule() throws Exception {
        GetPipelineResponseModel getPipelineResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);
        PipeLinesModel updatedPipeleine = new PipeLinesModel();
        List<PipeLinesModel> response = getPipelineResponseModel.getPipelines();
        updatedPipeleine.setLevel(response.get(0).getLevel());
        updatedPipeleine.setRules(response.get(0).getRules());
        updatedPipeleine.setEnabled(response.get(0).isEnabled());
        updatedPipeleine.setPipelineFor(response.get(0).getPipelineFor());
        updatedPipeleine.setType(response.get(0).getType());
        updatedPipeleine.setPipelineId(response.get(0).getPipelineId());
        connectionService.getResponseForPATCH(ENDPOINT_UPDATE_PIPELINE, updatedPipeleine);
    }

    @Then("^the response contains level '(.*)'$")
    public void reesponseIsMatchedToLevel(String level) {
        GetPipelineResponseModel getPipelineResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);
        List<PipeLinesModel> pipelines = getPipelineResponseModel.getPipelines();
        for (PipeLinesModel temp : pipelines)
            if (temp.isEnabled())                   //  we do checking if Pipeline is active
                assertThat(temp.getLevel().toString().contains(level)).isTrue();
    }

    @Then("^the Published Date corresponds to time of test execution$")
    public void publishDateCoresponds() {
        long time = System.currentTimeMillis();
        String expectationTime = String.valueOf(time).substring(0, 9);
        // Comment until we can connect to MongoDB
//        String actualTime =
//                databaseService
//                        .executeFind("dev_mongodb", "RulesConfiguration", "Pipelines", "Level", "AllMerchants");
//        assertThat(expectationTime).isEqualTo(actualTime);
    }

    @Then("^the fields of response with pipeline contains values:$")
    public void responseContainsPipeline(Map<String, String> dataTable) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.putAll(dataTable);

        GetPipelineResponseModel getPipelineResponseModel = connectionService
                .convertJsonToModel(resultState.getResult(), GetPipelineResponseModel.class);

        List<PipeLinesModel> response = getPipelineResponseModel.getPipelines();

        for (int i = 0; i < response.size(); i++) {
            assertThat(response.get(i).getLevel()).isOfAnyClassIn(TypeOfPipelineLevelModel.class);
            assertThat(response.get(i).getType()).isOfAnyClassIn(TypeOfPipelineModel.class);
            assertThat(response.get(i).isEnabled()).isOfAnyClassIn(Boolean.class);
            assertThat(response.get(i).getPipelineFor()).isInstanceOfAny(String.class);
            assertThat(response.get(i).isEnabled()).isOfAnyClassIn(Boolean.class);
            List<RulesResponseModel> list = response.get(i).getRules();
            for (RulesResponseModel elementOfList : list) {
                assertThat(elementOfList.getId()).isNotEmpty();
                assertThat(elementOfList.getName()).isNotEmpty();
                assertThat(elementOfList.getOrder() > 0).isTrue();
                assertThat(elementOfList.getLevel()).isNotEmpty();
                assertThat(elementOfList.getType()).isNotEmpty();
            }
        }
    }
}