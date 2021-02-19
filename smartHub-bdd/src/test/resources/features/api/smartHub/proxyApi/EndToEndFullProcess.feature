@smoke
Feature: Full end-to-end transaction
  As a Merchant
  I want to submit transactions
  So that I can check the integration between each component

  @DISE-934 @DISE-1103 @DISE-966
  Scenario: Trx registry verification: End-to-end processing of Cancel transaction authorizing request
    Given I 'cancel' a transaction with specific fields:
      | currency | USD |
    And the 'cancel' request should be recorded in Trx Registry
    And the 'cancel' response should be recorded in Trx Registry
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-1104 @DISE-966
  Scenario: Trx registry verification: End-to-end processing of Capture transaction authorizing request
    Given I 'capture' a transaction with specific fields:
      | currency | USD |
    And the 'capture' request should be recorded in Trx Registry
    And the 'capture' response should be recorded in Trx Registry
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-983 @DISE-984 @TC-1658 @TC-1697 @pending
  Scenario: Trx registry verification: End-to-end processing of Refund transaction authorizing request
    Given I send a 'debit' transaction request with random fields
    When I send a 'refund' transaction request with random fields
    Then the status code should be '200'
    And I convert the response to json for Proxy API
    And the 'refund' request should be recorded in Trx Registry
    And the 'refund' response should be recorded in Trx Registry