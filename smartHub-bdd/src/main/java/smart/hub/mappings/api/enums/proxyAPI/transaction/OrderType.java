package smart.hub.mappings.api.enums.proxyAPI.transaction;

import com.google.gson.annotations.SerializedName;

public enum OrderType {
    @SerializedName("01")
    GOODS_SERVICE_PURCHASE,
    @SerializedName("03")
    CHECK_ACCEPTANCE,
    @SerializedName("10")
    ACCOUNT_FUNDING,
    @SerializedName("11")
    QUASI_CASH_TRANSACTION,
    @SerializedName("28")
    PREPAID_ACTIVATION_AND_LOAN
}