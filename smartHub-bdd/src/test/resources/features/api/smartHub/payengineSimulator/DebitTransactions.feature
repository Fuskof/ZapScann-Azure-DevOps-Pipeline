Feature: Payengine simulator
  As a user I want that Payengine simulator (API endpoint) to be able to return (success, failure, pending) responses
  So that the trx processing for proxy integration to be tested


  @DISE-545
  Scenario: Debit transaction with Failure response
    Given I send a debit trx to Payengine simulator with the following fields
      | initialAmount | {INT=2} |
      | currency      | EUR     |
    Then the status code should be '200'
    And the body of the 'debit' response contains the appropriate values for status
      | transactions.status | FAILURE |

  @DISE-569
  Scenario: Debit transaction with Success response
    Given I send a debit trx to Payengine simulator with the following fields
      | currency      | EUR           |
    Then the status code should be '200'
    And the body of the 'debit' response contains the appropriate values for status
      | transactions.status | SUCCESS |

  @DISE-571
  Scenario: Debit transaction with Pending response
    Given I send a debit trx to Payengine simulator with the following fields
    #is >1000
      | initialAmount | {INT:10000..1000000} |
      | currency      | EUR                  |
    Then the status code should be '200'
    And the body of the 'debit' response contains the appropriate values for status
      | transactions.status | PENDING                 |
      | redirectUrl         | http://nets.pending.com |