Feature: Debit request validation
  As a Merchant,
  I want the Proxy API Gate to validate my Debit authorizing trx requests
  so that the trx requests received to have all the required data and the data to be in the expected format (in case of
  failure, relevant error messages are generated and sent to the merchant backend)

  @DISE-346 @DISE-537
  Scenario Outline: Debit transaction - validate error code <fieldErrorCode> and associated error message
    Given I send a 'debit' transaction request with specific fields:
      | <fieldPath> | <fieldValue> |
    Then the status code should be '400'
    And the response contains error code <fieldErrorCode> for field '<fieldPath>'
    Examples:
      | fieldPath                | fieldValue                       | fieldErrorCode |
      | initialAmount            | {NULL}                           | 12200          |
      | initialAmount            | {STRING:5}                       | 12201          |
      | initialAmount            | {REGEX:[1-9]{3}}                 | 12201          |
      | initialAmount            | {-INT:5}                         | 12201          |
      | initialAmount            | {LONG:19}                        | 12201          |
      | currency                 | {NULL}                           | 12202          |
      | currency                 | CCY                              | 12203          |
      | currency                 | eur                              | 12203          |
      | currency                 | {STRING:5}                       | 12203          |
      | currency                 | {INT:3}                          | 12203          |
      | product                  | {NULL}                           | 12256          |
      | product                  | {STRING:5}                       | 12257          |
      #| product                  | {INT:3}                          | 12257          | Defect: DISE-695
      | product                  | CREDITCARD                       | 12257          |
      | async                    | {NULL}                           | 12204          |
      | async.successUrl         | {NULL}                           | 12205          |
      | async.successUrl         | {LONG:10}                        | 12206          |
      #| async.successUrl         | {STRING:10}                      | 12206          | Defect: DISE-695
      | async.successUrl         | {REGEX:[a-z]\.com}               | 12206          |
      | async.successUrl         | {REGEX:[a-z]{2048}\.com}         | 12206          |
      | async.failureUrl         | {NULL}                           | 12207          |
      | async.failureUrl         | {LONG:10}                        | 12208          |
      #| async.failureUrl         | {STRING:10}                      | 12208          | Defect: DISE-695
      | async.failureUrl         | {REGEX:[a-z]\.com}               | 12208          |
      | async.failureUrl         | {REGEX:[a-z]{2048}\.com}         | 12208          |
      | async.cancelUrl          | {NULL}                           | 12209          |
      | async.cancelUrl          | {LONG:10}                        | 12210          |
      #| async.cancelUrl          | {STRING:10}                      | 12210          | Defect: DISE-695
      | async.cancelUrl          | {REGEX:[a-z]\.com}               | 12210          |
      | async.cancelUrl          | {REGEX:[a-z]{2048}\.com}         | 12210          |
      | description              | {EMPTY}                          | 12217          |
      | description              | {STRING:130}                     | 12217          |
      | description              | {INT:5}                          | 12217          |
      #| statementDescription     | {NULL}                           | no error message defined yet          |
      | statementDescription     | {EMPTY}                          | 12218          |
      | statementDescription     | {STRING:130}                     | 12218          |
      | statementDescription     | {INT:5}                          | 12218          |
      | basket                   | {STRING:5}                       | 12220          |
      | basket                   | {[3]STRING:5}                    | 12220          |
      | basket.name              | {NULL}                           | 12221          |
      | basket.name              | {EMPTY}                          | 12222          |
      | basket.name              | {STRING:130}                     | 12222          |
      | basket.name              | {INT:5}                          | 12222          |
      | basket.articleNumber     | {EMPTY}                          | 12224          |
      | basket.articleNumber     | {STRING:260}                     | 12224          |
      | basket.articleNumber     | {INT:5}                          | 12224          |
      | basket.totalPrice        | {-LONG:19}                       | 12226          |
      | basket.totalPrice        | {LONG:19}                        | 12226          |
      | basket.totalPrice        | {STRING:5}                       | 12226          |
      | basket.totalPrice        | {REGEX:[1-9]{2}}                 | 12226          |
      | basket.totalPriceWithTax | {-LONG:19}                       | 12228          |
      | basket.totalPriceWithTax | {LONG:19}                        | 12228          |
      | basket.totalPriceWithTax | {STRING:5}                       | 12228          |
      | basket.totalPriceWithTax | {REGEX:[1-9]{2}}                 | 12228          |
      | basket.unitPrice         | {-LONG:19}                       | 12232          |
      | basket.unitPrice         | {LONG:19}                        | 12232          |
      | basket.unitPrice         | {STRING:5}                       | 12232          |
      | basket.unitPrice         | {REGEX:[1-9]{2}}                 | 12232          |
      | basket.unitPriceWithTax  | {-LONG:19}                       | 12234          |
      | basket.unitPriceWithTax  | {LONG:19}                        | 12234          |
      | basket.unitPriceWithTax  | {STRING:5}                       | 12234          |
      | basket.unitPriceWithTax  | {REGEX:[1-9]{2}}                 | 12234          |
      | basket.tax               | {-INT:2}                         | 12230          |
      | basket.tax               | {-DOUBLE:2.2}                    | 12230          |
      | basket.tax               | {INT:4}                          | 12230          |
      | basket.tax               | {DOUBLE:4.2}                     | 12230          |
      | basket.tax               | {REGEX:[1-9]{2}}                 | 12230          |
      | basket.tax               | {STRING:5}                       | 12230          |
      | basket.quantity          | {-INT:1}                         | 12236          |
      | basket.quantity          | {LONG:10}                        | 12236          |
      | basket.quantity          | {STRING:5}                       | 12236          |
      | basket.quantity          | {REGEX:[1-9]{2}}                 | 12236          |
      | customer                 | {EMPTY}                          | 12238          |
      | customer                 | {STRING:260}                     | 12238          |
      | customer                 | {INT:5}                          | 12238          |
      | persona                  | {EMPTY}                          | 12241          |
      | persona                  | {STRING:260}                     | 12241          |
      | persona                  | {INT:5}                          | 12241          |
      | billingAddress           | {EMPTY}                          | 12245          |
      | billingAddress           | {STRING:260}                     | 12245          |
      | billingAddress           | {INT:5}                          | 12245          |
      | shippingAddress          | {EMPTY}                          | 12247          |
      | shippingAddress          | {STRING:260}                     | 12247          |
      | shippingAddress          | {INT:5}                          | 12247          |
      | ipAddress                | {NULL}                           | 12248          |
      | ipAddress                | {EMPTY}                          | 12249          |
      | ipAddress                | {STRING:260}                     | 12249          |
      | ipAddress                | {INT:5}                          | 12249          |
      | ipAddress                | {REGEX:([3-9]{3}\\.){3}[3-9]{3}} | 12249          |
      | ipAddress                | {REGEX:[0-9G-Z]{8}}              | 12249          |
      | channel                  | {NULL}                           | 12250          |
      | channel                  | moto                             | 12251          |
      | channel                  | ecom                             | 12251          |
      | channel                  | {STRNG:5}                        | 12251          |
      | channel                  | {INT:5}                          | 12251          |
      | payment                  | {NULL}                           | 12258          |
      | payment                  | {STRING:5}                       | 12259          |
      | payment.cardNumber       | {NULL}                           | 16000          |
      | payment.cardNumber       | {INT:8s}                         | 16001          |
      | payment.cardNumber       | {REGEX:[0-9]{22}}                | 16001          |
      | payment.cardNumber       | {STRING:14}                      | 16001          |
      | payment.cardNumber       | {LONG:16}                        | 16001          |
      | payment.verification     | {NULL}                           | 16002          |
      | payment.verification     | {INT:2s}                         | 16003          |
      | payment.verification     | {INT:6s}                         | 16003          |
      | payment.verification     | {STRING:3,4}                     | 16003          |
      | payment.verification     | {INT:3,4}                        | 16003          |
      | payment.expiryMonth      | {NULL}                           | 16004          |
      | payment.expiryMonth      | {INT:1s}                         | 16005          |
      | payment.expiryMonth      | {INT:13..24s}                    | 16005          |
      | payment.expiryMonth      | {STRING:2}                       | 16005          |
      | payment.expiryMonth      | {INT:1..12}                      | 16005          |
      | payment.expiryYear       | {NULL}                           | 16006          |
      | payment.expiryYear       | {INT:1s}                         | 16007          |
      | payment.expiryYear       | {INT:3s}                         | 16007          |
      | payment.expiryYear       | {STRING:2}                       | 16007          |
      #| payment.expiryYear       | {INT:1..99}                      | 16007          | Defect: DISE-695
      #| payment.cardHolder       | {EMPTY}                          | 16010          | Defect: DISE-695
      | payment.cardHolder       | {STRING:1,2}                     | 16010          |
      | payment.cardHolder       | {STRING:130}                     | 16010          |
      | payment.cardHolder       | {INT:5}                          | 16010          |
      | payment.cardHolder       | {SPECIAL:50}                     | 16010          |

  @DISE-346 @DISE-537
  Scenario Outline: Debit transaction - paymentInstrumentId and cardholder data invalid combinations
    Given I send a 'debit' transaction request with specific fields:
      | payment.paymentInstrumentId | <paymentInstrumentId> |
      | payment.cardNumber          | <cardNumber>          |
      | payment.verification        | <verification>        |
      | payment.expiryMonth         | <expiryMonth>         |
      | payment.expiryYear          | <expiryYear>          |
      | payment.cardHolder          | <cardHolder>          |
    Then the status code should be '400'
    And the response contains error code <fieldErrorCode> for field '<fieldPath>'
    Examples:
      | paymentInstrumentId | cardNumber          | verification     | expiryMonth            | expiryYear         | cardHolder  | fieldErrorCode | fieldPath                   |
      | {NULL}              | {NULL}              | {NULL}           | {NULL}                 | {NULL}             | {NULL}      | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | 4000090000000000000 | {NULL}           | {NULL}                 | {NULL}             | {NULL}      | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | {NULL}              | {REGEX:[0-9]{4}} | {NULL}                 | {NULL}             | {NULL}      | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | {NULL}              | {NULL}           | {REGEX:0[1-9]\|1[0-2]} | {NULL}             | {NULL}      | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | {NULL}              | {NULL}           | {NULL}                 | {REGEX:[3-9][0-9]} | {NULL}      | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | {NULL}              | {NULL}           | {NULL}                 | {NULL}             | {STRING:10} | 12263          | payment.paymentInstrumentId |
      | {STRING:10}         | 4000090000000000000 | {REGEX:[0-9]{4}} | {REGEX:0[1-9]\|1[0-2]} | {REGEX:[3-9][0-9]} | {STRING:10} | 12263          | payment.paymentInstrumentId |

  @DISE-346 @DISE-537
  Scenario: Debit transaction - paymentInstrumentId and no cardholder data
    Given I send a 'debit' transaction request with specific fields:
      | payment.paymentInstrumentId | {STRING:10} |
      | payment.cardNumber          | {NULL}      |
      | payment.verification        | {NULL}      |
      | payment.expiryMonth         | {NULL}      |
      | payment.expiryYear          | {NULL}      |
      | payment.cardHolder          | {NULL}      |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-346 @DISE-537
  Scenario Outline: Debit transaction - validate basket field with error code <fieldErrorCode> and associated error message
    Given I send a 'debit' transaction request with specific fields:
      | <fieldPath>     | <fieldValue> |
      | basket.discount | {-INT:3}     |
    Then the status code should be '400'
    And the response contains error code <fieldErrorCode> for field '<fieldPath>'
    Examples:
      | fieldPath                | fieldValue | fieldErrorCode |
      | basket.articleNumber     | {NULL}     | 12223          |
      | basket.totalPrice        | {NULL}     | 12225          |
      | basket.totalPriceWithTax | {NULL}     | 12227          |
      | basket.unitPrice         | {NULL}     | 12231          |
      | basket.unitPriceWithTax  | {NULL}     | 12233          |
      | basket.tax               | {NULL}     | 12229          |
      | basket.quantity          | {NULL}     | 12235          |

  @DISE-346 @DISE-537
  Scenario: Debit transaction - validate error code 16008 and associated error message
    Given I send a 'debit' transaction request with specific fields:
      | payment.expiryMonth | {REGEX:0[1-9]\|1[0-2]} |
      | payment.expiryYear  | {REGEX:0[1-9]\|1[0-9]} |
    Then the status code should be '400'
    And the response contains error code 16008 for field 'payment.expiryMonth&payment.expiryYear'

  @DISE-346 @DISE-537
  Scenario: Debit transaction - validate multiple error codes and associated error messages
    Given I send a 'debit' transaction request with specific fields:
      | initialAmount               | {-INT:2}                 |
      | product                     | {STRING:5}               |
      | async.successUrl            | {REGEX:[a-z]{2048}\.com} |
      | async.failureUrl            | {REGEX:[a-z]{2048}\.com} |
      | async.cancelUrl             | {REGEX:[a-z]{2048}\.com} |
      | description                 | {STRING:130}             |
      | statementDescription        | {STRING:130}             |
      | basket[0].name              | {STRING:130}             |
      | basket[0].articleNumber     | {STRING:260}             |
      | basket[0].totalPrice        | {-INT:2}                 |
      | basket[0].totalPriceWithTax | {-INT:2}                 |
      | basket[0].unitPrice         | {-INT:2}                 |
      | basket[0].unitPriceWithTax  | {-INT:2}                 |
      | basket[0].tax               | {INT:4}                  |
      | basket[0].quantity          | {-INT:1}                 |
      | customer                    | {STRING:260}             |
      | persona                     | {STRING:260}             |
      | billingAddress              | {STRING:260}             |
      | shippingAddress             | {STRING:260}             |
      | ipAddress                   | {STRING:260}             |
      | channel                     | {STRNG:5}                |
      | payment.cardNumber          | {REGEX:[0-9]{22}}        |
      | payment.verification        | {INT:2s}                 |
      | payment.expiryMonth         | {INT:13..24s}            |
      | payment.expiryYear          | {INT:1s}                 |
      | payment.cardHolder          | {STRING:130}             |
    Then the status code should be '400'
    And the 'debit' response contains error codes for all invalid fields

  @DISE-346 @DISE-537
  Scenario Outline: Debit transaction - validate values for fields with sets of accepted inputs
    Given I send a 'debit' transaction request with specific fields:
      | <fieldPath> | <fieldValue> |
    Then the status code should be '200'
    And I convert the response to json for Proxy API
    Examples:
      | fieldPath                             | fieldValue                           |
      | initialAmount                         | {INT=1}                              |
      | async.notifications.notificationUrn   | mailto:{FAKER:INTERNET.EMAILADDRESS} |
      | async.notifications.notificationState | {ARRAY:CREATED}                      |
      | async.notifications.notificationState | {ARRAY:UPDATED}                      |
      | async.notifications.notificationState | {ARRAY:CREATED,UPDATED}              |
      | async.notifications.notificationState | {ARRAY:UPDATED,CREATED}              |
      | basket.articleType                    | PHYSICAL                             |
      | basket.articleType                    | DIGITAL                              |
      | basket.articleType                    | DISCOUNT                             |
      | basket.articleType                    | SHIPPING_FEE                         |
      | basket.articleType                    | GIFT_CARD                            |
      | ipAddress                             | {FAKER:INTERNET.IPV6ADDRESS}         |
      | channel                               | MOTO                                 |
      | channel                               | ECOM                                 |
      | payment.cardNumber                    | {REGEX:[0-9]{4}( [0-9]{4}){3}}       |
      | payment.verification                  | {INT:3s}                             |
      | payment.verification                  | {INT:4s}                             |

  @DISE-346 @DISE-537
  Scenario: Debit transaction - validate accepted values for optional fields
    Given I send a 'debit' transaction request with specific fields:
      | async.notifications.notificationState | {ARRAY:CREATED}              |
      | description                           | {FAKER:HARRYPOTTER.QUOTE}    |
      | basket.discount                       | {-INT:1,5}                   |
      | basket.articleType                    | digital                      |
      | customer                              | {ALPHANUMERIC:8,12U}         |
      | persona                               | {ALPHANUMERIC:8,12U}         |
      | billingAddress                        | {FAKER:ADDRESS.FULLADDRESS}  |
      | shippingAddress                       | {FAKER:ADDRESS.FULLADDRESS}  |
      | ipAddress                             | {FAKER:INTERNET.IPV4ADDRESS} |
      | payment.cardHolder                    | {FAKER:NAME.FULLNAME}        |
    Then the status code should be '200'
    And I convert the response to json for Proxy API