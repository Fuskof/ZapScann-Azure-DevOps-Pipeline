package smart.hub.mappings.api.enums.proxyAPI.transaction;

import lombok.Getter;

@Getter
public enum TransactionResponseErrorCodes {

    ERROR_CODE_12000("root", "Error while creating order!"),
    ERROR_CODE_20("N/A", "Order/Payment instrument with such merchantOrderId/merchantPaymentInstrumentId already exists."),
    ERROR_CODE_12200("initialAmount", "Field initialAmount is missing. Expected: Positive integer between 1 and 999999999999999999."),
    ERROR_CODE_12201("initialAmount", "Bad value for initialAmount. Expected: Positive integer between 1 and 999999999999999999."),
    ERROR_CODE_12202("currency", "Field currency is missing. Expected: string value in ['EUR']."),
    ERROR_CODE_12203("currency", "Bad value for currency. Expected: string value in ['EUR']."),
    ERROR_CODE_12204("async", "Field async is missing. Expected: object of type Async."),
    ERROR_CODE_12205("async.successUrl", "Field async.successUrl is missing. Expected: string representing a valid URL."),
    ERROR_CODE_12206("async.successUrl", "Bad value for async.successUrl. Expected: string representing a valid URL."),
    ERROR_CODE_12207("async.failureUrl", "Field async.failureUrl is missing. Expected: string representing a valid URL."),
    ERROR_CODE_12208("async.failureUrl", "Bad value for async.failureUrl. Expected: string representing a valid URL."),
    ERROR_CODE_12209("async.cancelUrl", "Field async.cancelUrl is missing. Expected: string representing a valid URL."),
    ERROR_CODE_12210("async.cancelUrl", "Bad value for async.cancelUrl. Expected: string representing a valid URL."),
    ERROR_CODE_12211("async.transactionType", "Field async.transactionType is missing. Expected: string value in ['PREAUTH', 'DEBIT']."),
    ERROR_CODE_12212("async.transactionType", "Bad value for async.transactionType. Expected: string value in ['PREAUTH', 'DEBIT']."),
    ERROR_CODE_12213("terms", "Field terms is missing. Expected: UNIX timestamp in ms."),
    ERROR_CODE_12214("terms", "Bad value for terms. Expected: UNIX timestamp in ms."),
    ERROR_CODE_12215("privacy", "Field privacy is missing. Expected: UNIX timestamp in ms."),
    ERROR_CODE_12216("privacy", "Bad value for privacy. Expected: UNIX timestamp in ms."),
    ERROR_CODE_12217("description", "Bad value for description. Expected: string of length between 1 and 128."),
    ERROR_CODE_12218("statementDescription", "Bad value for statementDescription. Expected: string of length between 1 and 128."),
    ERROR_CODE_12219("basket", "Field basket is missing. Expected: array of type Item."),
    ERROR_CODE_12220("basket", "Bad value for basket. Expected: array of type Item."),
    ERROR_CODE_12221("basket.name", "Field basket.item.name is missing. Expected: string of length between 1 and 128."),
    ERROR_CODE_12222("basket.name", "Bad value for basket.name. Expected: string of length between 1 and 128."),
    ERROR_CODE_12223("basket.articleNumber", "Field basket.item.articleNumber is missing. Expected: string of length between 1 and 255."),
    ERROR_CODE_12224("basket.articleNumber", "Bad value for basket.articleNumber. Expected: string of length between 1 and 255."),
    ERROR_CODE_12225("basket.totalPrice", "Field basket.item.totalPrice is missing. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12226("basket.totalPrice", "Bad value for basket.totalPrice. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12227("basket.totalPriceWithTax", "Field basket.item.totalPriceWithTax is missing. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12228("basket.totalPriceWithTax", "Bad value for basket.totalPriceWithTax. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12229("basket.tax", "Field basket.item.tax is missing. Expected: positive integer between 1 and 100."),
    ERROR_CODE_12230("basket.tax", "Bad value for basket.item.tax. Expected: positive integer between 1 and 100."),
    ERROR_CODE_12231("basket.unitPrice", "Field basket.item.unitPrice is missing. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12232("basket.unitPrice", "Bad value for basket.unitPrice. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12233("basket.unitPriceWithTax", "Field basket.item.unitPriceWithTax is missing. Expected: Integer between -999999999999999998 and 999999999999999999."),
    ERROR_CODE_12234("basket.unitPriceWithTax", "Bad value for basket.unitPriceWithTax. Expected: positive integer between 1 and 999999999999999999."),
    ERROR_CODE_12235("basket.quantity", "Field basket.item.quantity is missing. Expected: positive integer between 1 and 9999999."),
    ERROR_CODE_12236("basket.quantity", "Bad value for basket.item.quantity. Expected: positive integer between 1 and 9999999."),
    ERROR_CODE_12237("customer", "Field customer is missing. Expected: A valid string identifier between 1 and 255 characters representing a Customer object."),
    ERROR_CODE_12238("customer", "Bad value for customer. Expected: A valid string identifier between 1 and 255 characters representing a Customer object."),
    ERROR_CODE_12239("customer", "Referenced object for customer invalid. Expected: A valid string identifier between 1 and 255 characters representing a Customer object."),
    ERROR_CODE_12240("persona", "Field persona is missing. Expected: A valid string identifier between 1 and 255 characters representing a Persona object."),
    ERROR_CODE_12241("persona", "Bad value for persona. Expected: A valid string identifier between 1 and 255 characters representing a Persona object."),
    ERROR_CODE_12242("persona", "Referenced object for persona invalid. Expected: A valid string identifier between 1 and 255 characters representing a Persona object"),
    ERROR_CODE_12244("billingAddress", "Field billingAddress is missing. Expected: A valid string identifier between 1 and 255 characters representing an Address object."),
    ERROR_CODE_12245("billingAddress", "Bad value for billingAddress. Expected: A valid string identifier between 1 and 255 characters representing an Address object."),
    ERROR_CODE_12246("shippingAddress", "Field shippingAddress is missing. Expected: A valid string identifier between 1 and 255 characters representing an Address object."),
    ERROR_CODE_12247("shippingAddress", "Bad value for shippingAddress. Expected: A valid string identifier between 1 and 255 characters representing an Address object."),
    ERROR_CODE_12248("ipAddress", "Field ipAddress is missing. Expected: A string between 1 and 255 characters representing an valid IPv4 - IPv6 address."),
    ERROR_CODE_12249("ipAddress", "Bad value for ipAddress. Expected: A string between 1 and 255 characters representing an valid IPv4 - IPv6 address."),
    ERROR_CODE_12250("channel", "Field channel is missing. Expected: A string value in ['MOTO', 'ECOM']."),
    ERROR_CODE_12251("channel", "Bad value for channel.  Expected: A string value in ['MOTO', 'ECOM']."),
    ERROR_CODE_12252("source", "Bad value for source.  Expected: string of length between 1 and 128 characters."),
    ERROR_CODE_12256("product", "Field product is missing. Expected: creditcard value."),
    ERROR_CODE_12257("product", "Bad value for product. Expected: creditcard"),
    ERROR_CODE_12258("payment", "Field payment is missing. Expected: A valid object of type PaymentInstrumentPayment."),
    ERROR_CODE_12259("payment", "Bad value for payment. Expected: object of type PaymentInstrumentPayment."),
    ERROR_CODE_12263("payment.paymentInstrumentId", "Field 'payment.paymentInstrumentId' and payment data provided. Please provide either a valid { product } payment instrument or { product } payment data."),
    ERROR_CODE_16000("payment.cardNumber", "Field payment.cardNumber is missing. Expected: string of length between 12 and 19 representing a valid creditcard number."),
    ERROR_CODE_16001("payment.cardNumber", "Bad value for payment.cardNumber. Expected: string of length between 12 and 19 representing a valid creditcard number."),
    ERROR_CODE_16002("payment.verification", "Field payment.verification is missing. Expected: string of length between 3 and 4 representing a valid creditcard verification number."),
    ERROR_CODE_16003("payment.verification", "Bad value for payment.verification. Expected: string of length between 3 and 4 representing a valid creditcard verification number."),
    ERROR_CODE_16004("payment.expiryMonth", "Field payment.expiryMonth is missing. Expected: string of length 2 between '01' and '12 representing the month in a valid creditcard expiry date."),
    ERROR_CODE_16005("payment.expiryMonth", "Bad value for payment.expiryMonth. Expected: string of length 2 between '01' and '12' representing the month in a valid creditcard expiry date."),
    ERROR_CODE_16006("payment.expiryYear", "Field payment.expiryYear is missing. Expected: string of length 2 between '00' and '99' representing the year in a valid creditcard expiry date."),
    ERROR_CODE_16007("payment.expiryYear", "Bad value for payment.expiryYear. Expected: string of length 2 between '00' and '99' representing the year in a valid creditcard expiry date."),
    ERROR_CODE_16008("payment.expiryMonth&payment.expiryYear", "Bad value for 'payment.expiryMonth' and 'payment.expiryYear'. Expected: valid creditcard expiry date >= current date."),
    ERROR_CODE_16009("payment.cardHolder", "Field payment.cardHolder is missing. Expected: string of length between 3 and 128 containing only alpha, whitespaces, dots, aphostrophes, dashes."),
    ERROR_CODE_16010("payment.cardHolder", "Bad value for payment.cardHolder. Expected: string of length between 3 and 128 containing only alpha, whitespaces, dots, aphostrophes, dashes."),
    ERROR_CODE_16011("payment.bankName", "Bad value for payment.bankName. Expected: string of length between 1 and 255 containing only alpha, whitespaces, numeric characters."),
    ERROR_CODE_16012("payment.iban", "Field payment.iban is missing. Expected: string of lentgth 22 representing a valid german IBAN."),
    ERROR_CODE_16013("payment.iban", "Bad value for payment.iban. Expected: string of lentgth 22 representing a valid german IBAN."),
    ERROR_CODE_16014("payment.bic", "Field payment.bic is missing. Expected: string of length 8 or 11 representing a valid BIC representing a valid german BIC."),
    ERROR_CODE_16015("payment.bic", "Bad value for payment.bic. Expected: string of length 8 or 11 representing a valid german BIC."),
    ERROR_CODE_16016("payment.accountHolder", "Bad value for payment.accountHolder. Expected: string of length between 4 and 128."),
    ERROR_CODE_16031("payment.countryCode", "Bad value for  payment.countryCode. Expected: 2 characters as per ISO 3166-1 alpha-2. Example: DE");

    private Integer errorCode;
    private String fieldPath;
    private String errorMessage;

    TransactionResponseErrorCodes(String fieldPath, String errorMessage) {
        this.errorCode = Integer.parseInt(this.name().split("ERROR_CODE_")[1]);
        this.fieldPath = fieldPath;
        this.errorMessage = errorMessage;
    }
}
