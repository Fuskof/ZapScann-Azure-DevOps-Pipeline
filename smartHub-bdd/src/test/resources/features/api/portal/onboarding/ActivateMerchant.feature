@smoke
Feature: Activate Merchant Account
  As a Admin
  I want to activate merchant account on Smart Hub
  So that this Merchant is able to proceed transactions

  @DISE-1008 @DISE-175
  Scenario: Activate child Merchant Account
    Given I send request to get external merchants
    And I save all merchants with status 'New' and hasChildren set to 'true'
    And I find and save a child of merchant having status set to 'New' and hasChildren set to 'false'
    When I board a child of the merchant
    And I assign to the merchant
      | type   | BusinessMID |
      | status | Active      |
    Then the status code should be '200'
    And the merchant status is 'Active' and type is 'BusinessMID'
