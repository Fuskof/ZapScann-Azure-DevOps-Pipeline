Feature: Merchant Pipeline UI/UX
  As a Admin
  I want to see the pipeline of the specific merchant
  So that all business rules applied to one merchant will be visible in one place

  @DISE-276
  Scenario: I send request to get pipeline with AllMerchants level
    When I request to get pipeline with fields:
      | level      | AllMerchants |
      | merchantId | 12343        |
    Then the status code should be '200'
    And the fields of response with pipeline contains values:
      | level       | AllMerchants  |
      | type        | Validation    |
      | enabled     | true          |
      | pipelineFor | All merchants |

  @DISE-276
  Scenario: I send request to get pipeline with Individual level
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    When I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 12343      |
    Then the status code should be '200'
    And the fields of response with pipeline contains values:
      | level       | Individual    |
      | type        | Validation    |
      | enabled     | false         |
      | pipelineFor | All merchants |

  @DISE-276
  Scenario: I send request to get pipeline
    Given  I send request to get merchants with status 'Active' and name 'External Trader1'
    When I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 22222      |
    Then the status code should be '200'
    And the fields of response with pipeline contains values:
      | level       | AllMerchants  |
      | type        | Validation    |
      | enabled     | false         |
      | pipelineFor | All merchants |

  @DISE-276
  Scenario: I send request to update pipeline
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    And I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 12343      |
    When I send put request to update rule
    Then the status code should be '200'

  @DISE-276
  Scenario: I send request to publish pipeline
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    And I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 12343      |
    When I send request to publish pipeline
    Then the status code should be '200'
