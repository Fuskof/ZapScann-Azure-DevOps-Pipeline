@DISE-733
Feature: Payengine Integration tests

  Scenario: Send minimum debit transaction to Payengine with credit card info
    Given I send a minimum 'debit' transaction to Payengine with specific fields:
      | payment.cardNumber   | 4111111111111111 |
      | payment.verification | 147              |
      | payment.expiryMonth  | 12               |
      | payment.expiryYear   | 22               |
      | payment.cardHolder   | Max Musterman    |
      | payment.cofContract  | {NULL}           |
      | meta                 | {NULL}           |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send full debit transaction to Payengine with credit card info
    Given I send a full 'debit' transaction to Payengine with specific fields:
      | customer                    | customer_01ijx79xmb |
      | persona                     | persona_bkrlhman4g  |
      | billingAddress              | address_3oicmcxv10  |
      | shippingAddress             | address_3oicmcxv10  |
      | payment.paymentInstrumentId | {NULL}              |
      | payment.cardNumber          | 4111111111111111    |
      | payment.verification        | 147                 |
      | payment.expiryMonth         | 12                  |
      | payment.expiryYear          | 22                  |
      | payment.cardHolder          | Max Musterman       |
      | payment.cofContract         | {NULL}              |
      | meta                        | {NULL}              |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send minimum debit transaction to Payengine with payment instrument info
    Given I send a minimum 'debit' transaction to Payengine with specific fields:
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send full debit transaction to Payengine with payment instrument info
    Given I send a full 'debit' transaction to Payengine with specific fields:
      | customer                    | customer_01ijx79xmb          |
      | persona                     | persona_bkrlhman4g           |
      | billingAddress              | address_3oicmcxv10           |
      | shippingAddress             | address_3oicmcxv10           |
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send invalid debit transaction to Payengine, then send a valid transaction with the same orderId
    Given I send a minimum 'debit' transaction to Payengine with specific fields:
      | initialAmount        | {STRING:5}       |
      | merchantOrderId      | {LONG:16s}       |
      | payment.cardNumber   | 4111111111111111 |
      | payment.verification | 147              |
      | payment.expiryMonth  | 12               |
      | payment.expiryYear   | 22               |
      | payment.cardHolder   | Max Musterman    |
      | payment.cofContract  | {NULL}           |
      | meta                 | {NULL}           |
    And the status code should be '400'
    When I send a minimum 'debit' transaction to Payengine with specific fields:
      | merchantOrderId      | {SERENITY:merchantOrderId} |
      | payment.cardNumber   | 4111111111111111           |
      | payment.verification | 147                        |
      | payment.expiryMonth  | 12                         |
      | payment.expiryYear   | 22                         |
      | payment.cardHolder   | Max Musterman              |
      | payment.cofContract  | {NULL}                     |
      | meta                 | {NULL}                     |
    And the status code should be '400'
    And the response contains error code 20

  Scenario: Send minimum pre-auth transaction to Payengine with credit card info
    Given I send a minimum 'pre-auth' transaction to Payengine with specific fields:
      | payment.cardNumber   | 4111111111111111 |
      | payment.verification | 147              |
      | payment.expiryMonth  | 12               |
      | payment.expiryYear   | 22               |
      | payment.cardHolder   | Max Musterman    |
      | payment.cofContract  | {NULL}           |
      | meta                 | {NULL}           |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send full pre-auth transaction to Payengine with credit card info
    Given I send a full 'pre-auth' transaction to Payengine with specific fields:
      | customer                    | customer_01ijx79xmb |
      | persona                     | persona_bkrlhman4g  |
      | billingAddress              | address_3oicmcxv10  |
      | shippingAddress             | address_3oicmcxv10  |
      | payment.paymentInstrumentId | {NULL}              |
      | payment.cardNumber          | 4111111111111111    |
      | payment.verification        | 147                 |
      | payment.expiryMonth         | 12                  |
      | payment.expiryYear          | 22                  |
      | payment.cardHolder          | Max Musterman       |
      | payment.cofContract         | {NULL}              |
      | meta                        | {NULL}              |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send minimum pre-auth transaction to Payengine with payment instrument info
    Given I send a minimum 'pre-auth' transaction to Payengine with specific fields:
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send full pre-auth transaction to Payengine with payment instrument info
    Given I send a full 'pre-auth' transaction to Payengine with specific fields:
      | customer                    | customer_01ijx79xmb          |
      | persona                     | persona_bkrlhman4g           |
      | billingAddress              | address_3oicmcxv10           |
      | shippingAddress             | address_3oicmcxv10           |
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  Scenario: Send invalid pre-auth transaction to Payengine, then send a valid transaction with the same orderId
    Given I send a full 'pre-auth' transaction to Payengine with specific fields:
      | initialAmount               | {STRING:5}                   |
      | merchantOrderId             | {LONG:16s}                   |
      | customer                    | customer_01ijx79xmb          |
      | persona                     | persona_bkrlhman4g           |
      | billingAddress              | address_3oicmcxv10           |
      | shippingAddress             | address_3oicmcxv10           |
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    And the status code should be '400'
    When I send a full 'pre-auth' transaction to Payengine with specific fields:
      | merchantOrderId             | {SERENITY:merchantOrderId}   |
      | customer                    | customer_01ijx79xmb          |
      | persona                     | persona_bkrlhman4g           |
      | billingAddress              | address_3oicmcxv10           |
      | shippingAddress             | address_3oicmcxv10           |
      | payment.paymentInstrumentId | paymentinstrument_h5n7stafce |
      | payment.cardNumber          | {NULL}                       |
      | payment.verification        | {NULL}                       |
      | payment.expiryMonth         | {NULL}                       |
      | payment.expiryYear          | {NULL}                       |
      | payment.cardHolder          | {NULL}                       |
      | payment.cofContract         | {NULL}                       |
      | meta                        | {NULL}                       |
    And the status code should be '400'
    And the response contains error code 20