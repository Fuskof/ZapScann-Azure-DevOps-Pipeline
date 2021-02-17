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

  @DISE-661 @DISE-1157
  Scenario:  Send transaction request using a non-existent merchant
    Given I construct an authentication header using the following values:
      | peMerchantId | PE01010101     |
      | apiKey       | ApiKey01010101 |
    And I send a 'debit' transaction request with random fields
    Then the status code should be '401'

  @DISE-661 @DISE-1164
  Scenario:  Send transaction request using an existing merchant but with an incorrect api key
    Given I construct an authentication header using the following values:
      | peMerchantId | PE5278397 |
      | apiKey       | ak5278397 |
    And I send a 'debit' transaction request with random fields
    Then the status code should be '401'

  @DISE-661 @DISE-1165
  Scenario:  Send transaction request without the Authorization header
    Given I send a 'debit' transaction request with no authorization
    Then the status code should be '401'

  @DISE-934 @DISE-966
  Scenario: End-to-end processing of Capture transaction authorizing request
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'capture' a transaction with specific fields:
      | currency      | USD           |
      | initialAmount | {LONG:6..999} |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-966
  Scenario: End-to-end processing of Cancel transaction authorizing request
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I 'cancel' a transaction with specific fields:
      | currency      | EUR           |
      | initialAmount | {LONG:6..999} |
    Then the status code should be '200'
    And I convert the response to json for Proxy API

  @DISE-934 @DISE-966
  Scenario: Send cancel transaction request without the Authorization header
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I send a 'cancel' transaction request with no authorization
    Then the status code should be '401'

  @DISE-934 @DISE-966
  Scenario: Send capture transaction request without the Authorization header
    Given I send a 'preauth' transaction request with specific fields:
      | initialAmount | {LONG:3..999} |
      | currency      | USD           |
    When I send a 'capture' transaction request with no authorization
    Then the status code should be '401'
