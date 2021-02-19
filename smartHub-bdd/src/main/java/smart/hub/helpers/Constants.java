package smart.hub.helpers;

public final class Constants {

    /*Constants for Wait time */
    public final static int MAX = 10;
    public final static int MIN = 5;

    /*Constants for img path */
    public final static String root = System.getProperty("user.dir");
    public final static String pathToFiles = root + "\\src\\main\\resources\\uploadFiles\\";

    /*Date patterns */
    public final static String TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    /*Endpoints */
    public final static String ENDPOINT_ADMIN_API = "adminapi/v1/api";
    public final static String ENDPOINT_PROXY_API = "proxyapi/v1";
    public final static String ENDPOINT_BATCH = "batch/v1";
    public final static String ENDPOINT_PORTAL_EXT_API = "portalextapi/v1/api";
    public final static String ENDPOINT_PE_SIMULATOR = "pesimulator/v1/api";

    /*PROXY API Endpoints */
    public final static String ENDPOINT_DEBIT_TRANSACTION = ENDPOINT_PROXY_API + "/orders/debit";
    public final static String ENDPOINT_PREAUTH_TRANSACTION = ENDPOINT_PROXY_API + "/orders/preauth";
    public final static String ENDPOINT_CANCEL_TRANSACTION = ENDPOINT_PROXY_API + "/orders/{orderId}/transactions/{transactionId}/cancel";
    public final static String ENDPOINT_CAPTURE_TRANSACTION = ENDPOINT_PROXY_API + "/orders/{orderId}/transactions/{transactionId}/capture";
    public final static String ENDPOINT_REFUND_TRANSACTION = ENDPOINT_PROXY_API + "/orders/{orderId}/transactions/{transactionId}/refund";

    /*ADMIN API Endpoints */
    public final static String ENDPOINT_UPDATE_INTEGRATION_MANAGER_CONFIGURATIONS = ENDPOINT_ADMIN_API + "/IntegrationManager/UpdateIntegrationManagerConfigurations";
    public final static String ENDPOINT_GET_INTEGRATION_MANAGER_CONFIGURATIONS = ENDPOINT_ADMIN_API + "/IntegrationManager/GetIntegrationManagerConfigurations?ConfigurationKey=";
    public final static String ENDPOINT_RULES_TEMPLATE_SAVE = ENDPOINT_ADMIN_API + "/RuleTemplates/divRuleEditor";
    public final static String ENDPOINT_RULES_TEMPLATE_EDIT = ENDPOINT_ADMIN_API + "/RuleTemplates/divRuleEditor";
    public final static String ENDPOINT_RULES_TEMPLATE_GET_ALL = ENDPOINT_ADMIN_API + "/RuleTemplates";
    public final static String ENDPOINT_GET_MERCHANTS = ENDPOINT_ADMIN_API + "/Merchant?status=";
    public final static String ENDPOINT_PUBLISH_RULES_TEMPLATES = ENDPOINT_ADMIN_API + "/RuleTemplates/publish";
    public final static String ENDPOINT_PUBLISHED_RULE_TEMPLATE_BY_ID = ENDPOINT_ADMIN_API + "/RuleTemplates/searchPublishedRuleTemplates?textToSearch=";
    public final static String ENDPOINT_SEARCH_RULE_TEMPLATE_BY_CRITERIA = ENDPOINT_ADMIN_API + "/RuleTemplates?searchTxt={text}&type={type}&status={status}";
    public final static String ENDPOINT_GET_BUSINESS_RULE_BY_ID = ENDPOINT_ADMIN_API + "/Rules/editorId/";
    public final static String ENDPOINT_API_RULE_GET_PIPELINES = ENDPOINT_ADMIN_API + "/Pipelines";
    public final static String ENDPOINT_ADD_RULE_TO_PIPELINE = ENDPOINT_ADMIN_API + "/Rules";
    public final static String ENDPOINT_MODIFY_A_BUSINESS_RULE = ENDPOINT_ADMIN_API + "/Rules";
    public final static String ENDPOINT_PUBLISH_PIPELINE = ENDPOINT_ADMIN_API + "/pipelines/publish";
    public final static String ENDPOINT_UPDATE_PIPELINE = ENDPOINT_ADMIN_API + "/pipelines";
    public final static String ENDPOINT_ENRICH_ONBOARDING_WITH_ENTITY_TYPE = ENDPOINT_ADMIN_API + "/Merchant/";
    public final static String ENDPOINT_ONBOARD_MERCHANTS = ENDPOINT_ADMIN_API + "/Onboarding";
    public final static String ENDPOINT_GET_ALL_EXTERNAL_MERCHANTS = ENDPOINT_ADMIN_API + "/Onboarding/withchildren";
    public final static String ENDPOINT_GET_VALUES_BY_COMPONENT_SOURCE_KEY = ENDPOINT_ADMIN_API + "/dictionaries/";

    /*BATCH PROCESSOR Endpoints */
    public final static String ENDPOINT_BATCH_PROCESSOR = ENDPOINT_BATCH + "/ProcessBatchFile";

    /*PORTAL EXT API Endpoints */
    public final static String ENDPOINT_GET_RULES_BY_MERCHANT_ID = ENDPOINT_PORTAL_EXT_API + "/Rule/GetRulesByMerchantId";
    public final static String ENDPOINT_GET_ALL_MERCHANTS = ENDPOINT_PORTAL_EXT_API + "/Merchant/GetAllMerchants";

    /*PE SIMULATOR Endpoints */
    public final static String ENDPOINT_PAYENGINE_SIMULATOR = ENDPOINT_PE_SIMULATOR + "/PayengineSimulator/ProcessTransaction";
    public final static String ENDPOINT_PE_CANCEL_TRANSACTION = ENDPOINT_PE_SIMULATOR + "/PayengineSimulator/orders/{orderId}/transactions/{transactionId}/cancel";
    public final static String ENDPOINT_PE_CAPTURE_TRANSACTION = ENDPOINT_PE_SIMULATOR + "/PayengineSimulator/orders/{orderId}/transactions/{transactionId}/capture";
    public final static String ENDPOINT_PE_REFUND_TRANSACTION = ENDPOINT_PE_SIMULATOR + "/PayengineSimulator/orders/{orderId}/transactions/{transactionId}/refund";
    public final static String ENDPOINT_PAYENGINE_DEBIT = "/orders/debit";
    public final static String ENDPOINT_PAYENGINE_PREAUTH = "/orders/preauth";
    public final static String ENDPOINT_PAYENGINE_BROWSER_INFO = "/browser-info";
    public final static String ENDPOINT_PAYENGINE_PI = ENDPOINT_PE_SIMULATOR + "/PaymentInstrumentSimulator/payment-instruments";
    public final static String ENDPOINT_PAYENGINE_PI_ID = ENDPOINT_PE_SIMULATOR + "/PaymentInstrumentSimulator/payment-instruments/{paymentInstrumentId}";


    /*Logging */
    public final static String LOG_SEPARATOR = "\n======================================================================\n";
}
