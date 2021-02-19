Feature: Verify record of trx request/ response in the Trx Registry
  As a Merchant, I want the DSS Hub to record the trx request/response in the Trx Registry

  @DISE-257 @DISE-837
  Scenario: Verify record of Debit trx in the Trx Registry
   Given I send a 'debit' transaction request with specific fields:
     | currency | EUR |
   Then the status code should be '200'
   And the 'debit' request should be recorded in Trx Registry
   And the 'debit' response should be recorded in Trx Registry

  @DISE-257 @DISE-837
  Scenario: Verify record of Pre-auth trx in the Trx Registry
    Given I send a 'pre-auth' transaction request with specific fields:
      | currency | USD |
    Then the status code should be '200'
    And the 'pre-auth' request should be recorded in Trx Registry
    And the 'pre-auth' response should be recorded in Trx Registry


  @DISE-934 @DISE-1103 @DISE-966
  Scenario: End-to-end processing of Cancel transaction authorizing request
    Given I 'cancel' a transaction with specific fields:
      | currency | USD |
    And the 'cancel' request should be recorded in Trx Registry
    And the 'cancel' response should be recorded in Trx Registry


  @DISE-934 @DISE-1104 @DISE-966
  Scenario: End-to-end processing of Capture transaction authorizing request
    Given I 'capture' a transaction with specific fields:
      | currency | USD |
    And the 'capture' request should be recorded in Trx Registry
    And the 'capture' response should be recorded in Trx Registry
