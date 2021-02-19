@sanity
Feature: Proxy API service response verification
  Main purpose for this endpoint is to transform the request payload into a canonical form.

  @DISE-492 @DISE-158 @DISE-661 @DISE-1154
  Scenario: End-to-end processing of Debit transaction authorizing request
    Given I send a 'debit' transaction request with random fields
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-492 @DISE-286 @DISE-661 @DISE-1163
  Scenario: End-to-end processing of Pre-auth transaction authorizing request
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-966
  Scenario: End-to-end processing of Capture transaction authorizing request
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD           |
    When I 'capture' a transaction with specific fields:
      | currency      | USD           |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-966
  Scenario: End-to-end processing of Cancel transaction authorizing request
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD           |
    When I 'cancel' a transaction with specific fields:
      | currency      | EUR           |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-983 @DISE-984 @TC-1657 @TC-1694 @pending
  Scenario: End-to-end processing of Refund request following a capture transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD           |
    And I 'capture' a transaction with specific fields:
      | currency      | USD           |
    When I send a 'refund' transaction request with random fields
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-983 @DISE-984 @TC-1658 @TC-1697 @pending
  Scenario: End-to-end processing of Refund request following a debit transaction
    Given I send a 'debit' transaction request with random fields
    When I send a 'refund' transaction request with random fields
    Then the status code should be '200'
    And I convert the response to json for Proxy API