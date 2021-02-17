Feature: Payengine simulator
  As a user I want that Payengine simulator (API endpoint) to be able to return (success, failure, pending) responses
  So that the trx processing for proxy integration to be tested

  @DISE-560
  Scenario: Pre-auth transaction with Failure response
    Given I send a pre-auth trx to Payengine simulator with the following fields
      | initialAmount | {INT=2} |
      | currency      | USD     |
    Then the status code should be '200'
    And the body of the 'pre-auth' response contains the appropriate values for status
      | transactions.status | FAILURE |

  @DISE-561
  Scenario: Pre-auth transaction with OK response
    Given I send a pre-auth trx to Payengine simulator with the following fields
      | initialAmount | {INT:3..1000} |
      | currency      | USD           |
    Then the status code should be '200'
    And the body of the 'pre-auth' response contains the appropriate values for status
      | transactions.status | OK |

  @DISE-546
  Scenario: Pre-auth transaction with Pending response
    Given I send a pre-auth trx to Payengine simulator with the following fields
    #is >1000
      | initialAmount | {INT:1000..100000} |
      | currency      | USD                |
    Then the status code should be '200'
    And the body of the 'pre-auth' response contains the appropriate values for status
      | transactions.status | PENDING                 |
      | redirectUrl         | http://nets.pending.com |
