Feature: Refund request validation
  As a Merchant,
  I want the Proxy API Gate to validate my Refund referencing trx requests
  so that the trx requests received to have all the required data and the data to be in the expected format (in case of
  failure, relevant error messages are generated and sent to the merchant backend)

  @DISE-982 @TC-1714
  Scenario Outline: Refund transaction - validate error code <fieldErrorCode> and associated error message
    Given I send a 'debit' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'refund' a transaction with specific fields:
      | <fieldPath> | <fieldValue> |
    Then the status code should be '400'
    And the response contains error code <fieldErrorCode> for field '<fieldPath>'
    Examples:
      | fieldPath                | fieldValue       | fieldErrorCode |
      | initialAmount            | {NULL}           | 12200          |
      | initialAmount            | {STRING:5}       | 12201          |
      | initialAmount            | {REGEX:[1-9]{3}} | 12201          |
      | initialAmount            | {-INT:5}         | 12201          |
      | initialAmount            | {LONG:19}        | 12201          |
      | currency                 | {NULL}           | 12202          |
      | currency                 | CCY              | 12203          |
      | currency                 | eur              | 12203          |
      | currency                 | {STRING:5}       | 12203          |
      | currency                 | {INT:3}          | 12203          |
      | description              | {EMPTY}          | 12217          |
      | description              | {STRING:130}     | 12217          |
      | description              | {INT:5}          | 12217          |
      | basket                   | {STRING:5}       | 12220          |
      | basket                   | {[3]STRING:5}    | 12220          |
      | basket.name              | {NULL}           | 12221          |
      | basket.name              | {EMPTY}          | 12222          |
      | basket.name              | {STRING:130}     | 12222          |
      | basket.name              | {INT:5}          | 12222          |
      | basket.articleNumber     | {EMPTY}          | 12224          |
      | basket.articleNumber     | {STRING:260}     | 12224          |
      | basket.articleNumber     | {INT:5}          | 12224          |
      | basket.totalPrice        | {-LONG:19}       | 12226          |
      | basket.totalPrice        | {LONG:19}        | 12226          |
      | basket.totalPrice        | {STRING:5}       | 12226          |
      | basket.totalPrice        | {REGEX:[1-9]{2}} | 12226          |
      | basket.totalPriceWithTax | {-LONG:19}       | 12228          |
      | basket.totalPriceWithTax | {LONG:19}        | 12228          |
      | basket.totalPriceWithTax | {STRING:5}       | 12228          |
      | basket.totalPriceWithTax | {REGEX:[1-9]{2}} | 12228          |
      | basket.unitPrice         | {-LONG:19}       | 12232          |
      | basket.unitPrice         | {LONG:19}        | 12232          |
      | basket.unitPrice         | {STRING:5}       | 12232          |
      | basket.unitPrice         | {REGEX:[1-9]{2}} | 12232          |
      | basket.unitPriceWithTax  | {-LONG:19}       | 12234          |
      | basket.unitPriceWithTax  | {LONG:19}        | 12234          |
      | basket.unitPriceWithTax  | {STRING:5}       | 12234          |
      | basket.unitPriceWithTax  | {REGEX:[1-9]{2}} | 12234          |
      | basket.tax               | {-INT:2}         | 12230          |
      | basket.tax               | {-DOUBLE:2.2}    | 12230          |
      | basket.tax               | {INT:4}          | 12230          |
      | basket.tax               | {DOUBLE:4.2}     | 12230          |
      | basket.tax               | {REGEX:[1-9]{2}} | 12230          |
      | basket.tax               | {STRING:5}       | 12230          |
      | basket.quantity          | {-INT:1}         | 12236          |
      | basket.quantity          | {LONG:10}        | 12236          |
      | basket.quantity          | {STRING:5}       | 12236          |
      | basket.quantity          | {REGEX:[1-9]{2}} | 12236          |

  @DISE-982 @TC-1714
  Scenario Outline: Refund transaction - validate basket field with error code <fieldErrorCode> and associated error message
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'refund' a transaction with specific fields:
      | <fieldPath>     | <fieldValue> |
      | basket.discount | {-INT:3}     |
    Then the status code should be '400'
    And the response contains error code <fieldErrorCode> for field '<fieldPath>'
    Examples:
      | fieldPath            | fieldValue | fieldErrorCode |
      | basket.articleNumber | {NULL}     | 12223          |
      | basket.tax           | {NULL}     | 12230          |
      | basket.quantity      | {NULL}     | 12236          |

  @DISE-982 @TC-1714
  Scenario: Refund transaction - validate multiple error codes and associated error messages
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'refund' a transaction with specific fields:
      | initialAmount           | {-INT:2}     |
      | description             | {STRING:130} |
      | basket[0].name          | {STRING:130} |
      | basket[0].articleNumber | {STRING:260} |
#      | basket[0].totalPrice        | {-INT:2}     |
#      | basket[0].totalPriceWithTax | {-INT:2}     |
#      | basket[0].unitPrice         | {-INT:2}     |
#      | basket[0].unitPriceWithTax  | {-INT:2}     |
      | basket[0].tax           | {INT:4}      |
      | basket[0].quantity      | {-INT:1}     |
    Then the status code should be '400'
    And the 'refund' response contains error codes for all invalid fields

  @DISE-982 @TC-1713 @pending
  Scenario Outline: Refund transaction - validate values for fields with sets of accepted inputs
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'refund' a transaction with specific fields:
      | <fieldPath> | <fieldValue> |
    Then the status code should be '200'
    And I convert the response to json for Proxy API
    Examples:
      | fieldPath          | fieldValue   |
      | initialAmount      | {INT=1}      |
      | basket.articleType | PHYSICAL     |
      | basket.articleType | DIGITAL      |
      | basket.articleType | DISCOUNT     |
      | basket.articleType | SHIPPING_FEE |
      | basket.articleType | GIFT_CARD    |

  @DISE-982 @TC-1713 @pending
  Scenario: Refund transaction - validate accepted values for optional fields
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'refund' a transaction with specific fields:
      | description        | {FAKER:CHUCKNORRIS.FACT} |
      | basket.discount    | {-INT:1,5}                |
      | basket.articleType | digital                   |
    Then the status code should be '200'
    And I convert the response to json for Proxy API