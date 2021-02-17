Feature: View business rule mode
  As a Business rule expert
  I want to have a read form of a business rule
  So that each template have the option to be read-only

  @DISE-639
  Scenario: I send get request to view business rule mode
    Given I send request to get merchants with status 'Active' and name 'External Trader1'
    And I request to get pipeline with fields:
      | level      | Individual |
      | merchantId | 12343      |
    When I send request to view business rule mode
    Then the status code should be '200'
    And response contains rule id
