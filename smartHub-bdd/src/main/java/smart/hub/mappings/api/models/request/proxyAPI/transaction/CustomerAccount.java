
package smart.hub.mappings.api.models.request.proxyAPI.transaction;


import lombok.Getter;
import lombok.Setter;
import smart.hub.helpers.generators.GenerateValue;
import smart.hub.mappings.api.enums.proxyAPI.transaction.*;

import static smart.hub.helpers.Constants.DATE_PATTERN_YYYY_MM_DD;

@Getter
@Setter
public class CustomerAccount {

    @GenerateValue("{ALPHANUMERIC:8,15}")
    private Object accountIdentifier;

    @GenerateValue(value = "{TODAY-3m}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object creationDate;

    @GenerateValue(value = "{TODAY-10d}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object lastChangeDate;

    @GenerateValue(type = ChangeIndicator.class)
    private Object changeIndicator;

    @GenerateValue(value = "{TODAY-10d}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object lastPasswordChangeDate;

    @GenerateValue(type = PasswordChangeIndicator.class)
    private Object passwordChangeIndicator;

    @GenerateValue(type = AuthenticationMethod.class)
    private Object authenticationMethod;

    @GenerateValue("{NOW-4h}")
    private Object authenticationTimestamp;

    @GenerateValue(type = ShippingAddressUsageIndicator.class)
    private Object shippingAddressUsageIndicator;

    @GenerateValue(value = "{TODAY-14d}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object shippingAddressFirstUsage;

    @GenerateValue(type = Integer.class, value = "{INT=1}")
    private Object transactionCountLastDay;

    @GenerateValue(type = Integer.class, value = "{INT:6..10}")
    private Object transactionCountLastYear;

    @GenerateValue(type = Integer.class, value = "{INT:1..5}")
    private Object orderCountLast6Months;

    @GenerateValue(type = Boolean.class, value = "{FALSE}")
    private Object suspiciousActivity;

    @GenerateValue(type = Boolean.class, value = "{TRUE}")
    private Object accountEqualsShippingName;

    @GenerateValue(type = PaymentAccountAgeIndicator.class)
    private Object paymentAccountAgeIndicator;

    @GenerateValue(value = "{TODAY-9d}", dateFormat = DATE_PATTERN_YYYY_MM_DD)
    private Object paymentAccountEnrollementDate;

}
