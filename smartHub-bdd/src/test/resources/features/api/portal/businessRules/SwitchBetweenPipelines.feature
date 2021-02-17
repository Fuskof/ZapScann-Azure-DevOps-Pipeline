Feature: Switch between pipelines
  As a Admin
  I want to see the pipeline of the specific merchant
  So that all business rules applied to one merchant will be visible in one place

  @DISE-584
  Scenario: I send request to get pipeline with level AllMerchants
    Given I request to get pipeline with fields:
      | level      | AllMerchants |
      | merchantId | 12343        |
    Then the status code should be '200'
    And the response contains level 'AllMerchants'

  @DISE-584
  Scenario: I send request to get pipeline with level Individual
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    When I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 12343      |
    Then the status code should be '200'
    And the response contains level 'Individual'

  @DISE-584
  Scenario: I try to send request to get pipeline with level Individual and empty text to searching
    When I request to get pipeline with fields:
      | level | Individual |
    Then the status code should be '500'

  @DISE-584
  Scenario: I send request to get pipeline with level AllMerchants and wrong id
    Given I request to get pipeline with fields:
      | level      | AllMerchants |
      | merchantId | WrongNumber  |
    Then the status code should be '200'
    And the response contains level 'AllMerchants'

  @DISE-584
  Scenario: I try to send request to get pipeline with level Individual and wrong id
    Given I request to get pipeline with fields:
      | level      | Individual  |
      | merchantId | WrongNumber |
    Then the status code should be '500'

  @DISE-584
  Scenario: I try to send request to get pipeline with wrong level
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    When I request to get pipeline with fields:
      | level      | WrongTest |
      | merchantId | 12343     |
    Then the status code should be '400'