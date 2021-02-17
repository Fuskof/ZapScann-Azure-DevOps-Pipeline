Feature: Payengine simulator
  As a user I want that Payengine simulator (API endpoint) to be able to return responses
  So that the payment instruments processing for proxy integration to be tested

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
  @DISE-963 @DISE-1346
  Scenario: Create a Payment instruments with Success response
    Given I send a post payment instrument request with random fields to Payengine simulator
    Then the status code should be '200'

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
  @DISE-963 @DISE-1349
  Scenario: Send a Payment instruments and receive a Failure response
    Given I send a post payment instrument request with the following fields to Payengine simulator
      | payment.cardNumber | 777777777 |
    Then the status code should be '400'

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
  @DISE-963 @DISE-1350
  Scenario: Send a Payment instruments and receive a Failure response
    Given I send a post payment instrument request with the following fields to Payengine simulator
      | payment.cardNumber | 000000000000000 |
    Then the status code should be '500'

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
 @DISE-963 @DISE-1364
  Scenario: Update a Payment instruments and receive a Success response
    Given I patch a payment instrument with id 'oid' having random fields to Payengine simulator
      | payment.cardNumber | 597533746820038802 |
      | payment.verification | {INT=1234} |
    Then the status code should be '200'

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
  @DISE-963 @DISE-1365
  Scenario: Update a Payment instruments and receive a Failure response
    Given I patch payment instrument request with the following fields to Payengine simulator
      | payment.verification | {INT=12345} |
    Then the status code should be '400'

#POST and Patch are impacted by the final version of the PI contract and to be retested after DISE-660 will be implemented
  @DISE-963 @DISE-1368
  Scenario: Update a Payment instruments and receive a Failure response
    Given I patch payment instrument request with the following fields to Payengine simulator
      | payment.cardNumber | 0000000000000000 |
    Then the status code should be '500'

  @DISE-963 @DISE-1369
  Scenario: Send a Get Payment instruments with Success response
    Given I send a get payment instrument request with a valid PIid to Payengine simulator
    Then the status code should be '200'

  @DISE-963 @DISE-1370
  Scenario: Send a Get Payment instruments with Failure response
    Given I send a get payment instrument request with an invalid PIid to Payengine simulator
    Then the status code should be '404'

  @DISE-963 @DISE-1371
  Scenario: Send a Get Payment instruments with Failure response
    Given I send a get payment instrument request with a specific PIid to Payengine simulator
    Then the status code should be '500'

  @DISE-963 @DISE-1372
  Scenario: Send a GetAll Payment instruments with Success response
    Given I send a 'success' getAll payment instruments request to Payengine simulator
    Then the status code should be '200'

  @DISE-963 @DISE-1373
  Scenario: Send a GetAll Payment instruments with Failure response
    Given I send a 'failure' getAll payment instruments request to Payengine simulator
    Then the status code should be '404'

  @DISE-963 @DISE-1374
  Scenario: Delete a Payment instruments with a valid paymentInstrumentId
    Given I send a delete payment instrument request with a valid PIid to Payengine simulator
    Then the status code should be '204'

  @DISE-963 @DISE-1375
  Scenario: Delete a Payment instruments with an invalid paymentInstrumentId
    Given I send a delete payment instrument request with an invalid PIid to Payengine simulator
    Then the status code should be '404'

  @DISE-963 @DISE-1377
  Scenario: Delete a Payment instruments with Failure response
    Given I send a delete payment instrument request with a specific PIid to Payengine simulator
    Then the status code should be '500'

  @DISE-963 @DISE-1478
  Scenario: Delete a Payment instruments without paymentInstrumentId
    Given I send a delete payment instrument request without PIid to Payengine simulator
    Then the status code should be '405'
