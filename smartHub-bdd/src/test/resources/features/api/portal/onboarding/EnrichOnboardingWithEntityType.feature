@smoke
Feature: Enrich Onboarding with Entity Type
  As a Admin
  I want to be have entity type in the merchant accounts
  So that system will identify correct level of the merchant account

  @DISE-881 @DISE-161  @DISE-289 @DISE-175
  Scenario: I change type of inactive merchant to Partner
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'InProgress'
      | type | Partner |
    Then the status code should be '200'
    And the type of changed merchant is 'Partner'

  @DISE-881 @DISE-161  @DISE-289 @DISE-175
  Scenario: I change type of inactive merchant to MarketingGroup
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'InProgress'
      | type | MarketingGroup |
    Then the status code should be '200'
    And the type of changed merchant is 'MarketingGroup'

  @DISE-881 @DISE-161  @DISE-289 @DISE-175
  Scenario: I change type of inactive merchant to LegalEntity
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'InProgress'
      | type | LegalEntity |
    Then the status code should be '200'
    And the type of changed merchant is 'LegalEntity'

  @DISE-881 @DISE-161  @DISE-289 @DISE-175
  Scenario: I change type of inactive merchant to BusinessMID
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'InProgress'
      | type | BusinessMID |
    Then the status code should be '200'
    And the type of changed merchant is 'BusinessMID'

  @DISE-881 @DISE-161
  Scenario: I try to change type of merchant to wrongValue
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'InProgress'
      | type | wrongValue |
    Then the status code should be '400'

  @DISE-881 @DISE-161 @DISE-175
  Scenario: I try to change active type of merchant to Partner
    Given I send request to get external merchants
    When I send request to change type of merchant with status 'Active'
      | type | Partner |
    Then the status code should be '400'