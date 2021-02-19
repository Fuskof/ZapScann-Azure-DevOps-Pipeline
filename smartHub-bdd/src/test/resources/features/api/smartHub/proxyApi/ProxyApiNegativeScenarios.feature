@smoke
Feature: Negative scenarios for all transaction types
  As a merchant
  I want ProxyAPI to handle negative scenarios for all transaction types
  So that transactions with missing/incorrect authorization
  Or transactions requested for non-existent orders
  Are handled properly
  And the relevant error messages are returned

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
  Scenario: Send cancel transaction request without the Authorization header
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'cancel' transaction request with no authorization
    Then the status code should be '401'

  @DISE-934 @DISE-966
  Scenario: Send capture transaction request without the Authorization header
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'capture' transaction request with no authorization
    Then the status code should be '401'

  @DISE-1096 @TC-1739 @pending
  Scenario: Send refund transaction request for non existing orderId
    Given I send a 'debit' transaction request with random fields
    When I send a 'refund' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {NULL}        |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1096 @TC-1740 @pending
  Scenario: Send refund transaction request for non existing transactionId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'refund' transaction request with custom path parameters:
      | orderId       | {NULL}        |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1096 @TC-1741 @pending
  Scenario: Send refund transaction request for non existing orderId and transactionId
    Given I send a 'debit' transaction request with random fields
    When I send a 'refund' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1096 @TC-1742 @pending
  Scenario: Send refund transaction request following a failed debit transaction
    Given I send a 'debit' transaction request with specific fields:
      | initialAmount | {INT=2} |
    When I send a 'refund' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1096 @TC-1743 @pending
  Scenario: Send refund transaction request following a pending debit transaction
    Given I send a 'debit' transaction request with specific fields:
      | initialAmount | {INT:1001..9999} |
    When I send a 'refund' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1096 @TC-1744 @pending
  Scenario: Send refund transaction request following an expired capture transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    And I send a 'capture' transaction request with specific fields:
      | initialAmount | {INT=5} |
    When I send a 'refund' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1095 @TC-1747 @pending
  Scenario: Send capture transaction request for non existing orderId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'capture' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {NULL}        |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1748 @pending
  Scenario: Send capture transaction request for non existing transactionId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'capture' transaction request with custom path parameters:
      | orderId       | {NULL}        |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1749 @pending
  Scenario: Send capture transaction request for non existing orderId and transactionId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'capture' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1753 @pending
  Scenario: Send capture transaction request following a failed preauth transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD     |
      | initialAmount | {INT=2} |
    When I send a 'capture' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1095 @TC-1754 @pending
  Scenario: Send capture transaction request following a pending preauth transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD              |
      | initialAmount | {INT:1001..9999} |
    When I send a 'capture' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1095 @TC-1750 @pending
  Scenario: Send cancel transaction request for non existing orderId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'cancel' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {NULL}        |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1751 @pending
  Scenario: Send cancel transaction request for non existing transactionId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'cancel' transaction request with custom path parameters:
      | orderId       | {NULL}        |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1752 @pending
  Scenario: Send cancel transaction request for non existing orderId and transactionId
    Given I send a 'preauth' transaction request with specific fields:
      | currency | USD |
    When I send a 'cancel' transaction request with custom path parameters:
      | orderId       | {STRING:5,10} |
      | transactionId | {STRING:5,10} |
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. The related underlying transaction with the provided transaction ID does not exist |

  @DISE-1095 @TC-1755 @pending
  Scenario: Send cancel transaction request following a failed preauth transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD     |
      | initialAmount | {INT=2} |
    When I send a 'cancel' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |

  @DISE-1095 @TC-1756 @pending
  Scenario: Send cancel transaction request following a pending preauth transaction
    Given I send a 'preauth' transaction request with specific fields:
      | currency      | USD              |
      | initialAmount | {INT:1001..9999} |
    When I send a 'cancel' transaction request with random fields
    Then the status code should be '404'
    And the response body is:
      | The request can not be processed. Not expected status of the related underlying transaction |