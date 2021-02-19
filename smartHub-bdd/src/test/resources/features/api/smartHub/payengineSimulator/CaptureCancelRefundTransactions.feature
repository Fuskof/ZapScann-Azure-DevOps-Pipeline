Feature: Payengine simulator
  As a user I want that Payengine simulator (API endpoint) to be able to return (success, failure, expired) responses
  So that the trx processing for proxy integration to be tested


  @DISE-958 @DISE-1106
  Scenario: Cancel transaction with Expired response
    Given I send a 'cancel' trx to Payengine simulator with the following fields
      | initialAmount | {INT=5} |
      | currency      | EUR     |
    Then the status code should be '200'
    And the body of the 'cancel' response contains the appropriate values for status
      | status | EXPIRED |

  @DISE-958 @DISE-1107
  Scenario: Cancel transaction with Succes response
    Given I send a 'cancel' trx to Payengine simulator with the following fields
      | currency | EUR |
    Then the status code should be '200'
    And the body of the 'cancel' response contains the appropriate values for status
      | status | SUCCESS |

  @DISE-958 @DISE-1108
  Scenario: Capture transaction with Expired response
    Given I send a 'capture' trx to Payengine simulator with the following fields
      | initialAmount | {INT=5} |
      | currency      | USD     |
    Then the status code should be '200'
    And the body of the 'capture' response contains the appropriate values for status
      | status | EXPIRED |

  @DISE-958 @DISE-1109
  Scenario: Capture transaction with Success response
    Given I send a 'capture' trx to Payengine simulator with the following fields
      | currency | USD |
    Then the status code should be '200'
    And the body of the 'capture' response contains the appropriate values for status
      | status | SUCCESS |

  @DISE-985 @DISE-1692
  Scenario: Refund transaction - expected status SUCCESS
    Given I send a 'refund' trx to Payengine simulator with the following fields
      | currency | EUR |
    Then the status code should be '200'
    And the body of the 'refund' response contains the appropriate values for status
      | status | SUCCESS |

  @DISE-985 @DISE-1693
  Scenario: Refund transaction - expected status FAILURE
    Given I send a 'refund' trx to Payengine simulator with the following fields
      | initialAmount | {INT=5} |
    Then the status code should be '200'
    And the body of the 'refund' response contains the appropriate values for status
      | status | FAILURE |
