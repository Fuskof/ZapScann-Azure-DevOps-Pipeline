package smart.hub.mappings.api.enums.proxyAPI.transaction;

import com.google.gson.annotations.SerializedName;

public enum PaymentInstrumentType {
    creditcard,
    paypal,
    paydirekt,
    @SerializedName("bank-transfer")
    bank_transfer
}
