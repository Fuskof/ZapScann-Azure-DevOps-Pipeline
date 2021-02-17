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
    And the trx response should be recorded in Trx Registry
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-1104 @DISE-966
  Scenario: Trx registry verification: End-to-end processing of Capture transaction authorizing request
    Given I 'capture' a transaction with specific fields:
      | currency | USD |
    And the 'capture' request should be recorded in Trx Registry
    And the trx response should be recorded in Trx Registry
    Then the status code should be '200'
    And I convert the response to json for Proxy API