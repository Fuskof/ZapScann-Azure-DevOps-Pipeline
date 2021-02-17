package smart.hub.config;


import io.cucumber.java.DataTableType;
import net.serenitybdd.core.Serenity;
import smart.hub.helpers.generators.JsonObjectGenerator;
import smart.hub.mappings.api.models.request.adminAPI.RuleConfigurationSaveRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.integrationManager.UpdateConfigurationsRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.merchants.ChangeMerchantStatusModel;
import smart.hub.mappings.api.models.request.adminAPI.merchants.MerchantBodyModel;
import smart.hub.mappings.api.models.request.adminAPI.merchants.OnBoardMerchantModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.AddRuleToPipelineRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.AttributesOfModifyRuleModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.AttributesOfRuleModel;
import smart.hub.mappings.api.models.request.adminAPI.pipelines.ModifyBusinessRuleRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplateEditRequestModel;
import smart.hub.mappings.api.models.request.adminAPI.ruleTemplates.RuleTemplateSaveRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.paymentInstrument.PaymentInstrumentRequest;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.CancelCaptureRefundRequestModel;
import smart.hub.mappings.api.models.request.proxyAPI.transaction.DebitPreauthRequestModel;
import smart.hub.mappings.serviceBus.models.rules.MerchantRulesModel;

import java.util.Map;

public class CustomTypes {

    @DataTableType
    public DebitPreauthRequestModel generateDebitPreauthTransactionModel(Map<String, String> entry) {
        Serenity.setSessionVariable("debitPreauthRequestDataTable").to(entry);
        return JsonObjectGenerator.generateJsonObject(DebitPreauthRequestModel.class, entry);
    }

    @DataTableType
    public CancelCaptureRefundRequestModel generateCancelCaptureRefundTransactionModel(Map<String, String> entry) {
        Serenity.setSessionVariable("cancelCaptureRefundRequestDataTable").to(entry);
        return JsonObjectGenerator.generateJsonObject(CancelCaptureRefundRequestModel.class, entry);
    }

    @DataTableType
    public PaymentInstrumentRequest generatePaymentInstrumentRequest(Map<String, String> entry) {
        Serenity.setSessionVariable("paymentInstrumentRequestDataTable").to(entry);
        return JsonObjectGenerator.generateJsonObject(PaymentInstrumentRequest.class, entry);
    }

    @DataTableType
    public UpdateConfigurationsRequestModel updateConfigurationsModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(UpdateConfigurationsRequestModel.class, entry);
    }

    @DataTableType
    public RuleConfigurationSaveRequestModel saveRequestModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(RuleConfigurationSaveRequestModel.class, entry);
    }

    @DataTableType
    public RuleTemplateSaveRequestModel generateRuleTemplateModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(RuleTemplateSaveRequestModel.class, entry);
    }

    @DataTableType
    public MerchantRulesModel generateMerchantRulesModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(MerchantRulesModel.class, entry);
    }

    @DataTableType
    public OnBoardMerchantModel generateOnBoardMerchantModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(OnBoardMerchantModel.class, entry);
    }

    @DataTableType
    public ChangeMerchantStatusModel generateChangeMerchantStatusModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(ChangeMerchantStatusModel.class, entry);
    }

    @DataTableType
    public MerchantBodyModel generateMMerchantBodyModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(MerchantBodyModel.class, entry);
    }

    @DataTableType
    public AddRuleToPipelineRequestModel addRuleToPipelineModelRequest(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(AddRuleToPipelineRequestModel.class, entry);
    }

    @DataTableType
    public AttributesOfRuleModel attributesOfRuleModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(AttributesOfRuleModel.class, entry);
    }

    @DataTableType
    public RuleTemplateEditRequestModel ruleTemplateEditRequestModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(RuleTemplateEditRequestModel.class, entry);
    }

    @DataTableType
    public ModifyBusinessRuleRequestModel modifyBusinessRuleRequestModel(Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(ModifyBusinessRuleRequestModel.class, entry);
    }

    @DataTableType
    public AttributesOfModifyRuleModel attributesOfModifyRuleModel (Map<String, String> entry) {
        return JsonObjectGenerator.generateJsonObject(AttributesOfModifyRuleModel.class, entry);
    }



}