@smoke
Feature: Enable merchant
  As a Admin
  I want to be able to enable merchant on Smart Hub
  So that he will be able to use the full functionality of the Hub

  @DISE-175
  Scenario: I send post request to boarding merchant
    Given I send request to get external merchants
    And I find a merchant with status 'Inactive'
    When I send post request to boarding merchant
    Then the status code should be '200'
    And the response contains id of boarded merchant

  @DISE-175
  Scenario Outline: I try to change merchant's status with different combinations
    Given I send request to get external merchants
    And I find a merchant with status '<FirstStatus>'
    When I send put request to assign to merchant status '<SecondStatus>'
    Then the status code should be '<ActiveCode>'
    And I send put request to assign to merchant status '<FirstStatus>'
    Examples:
      | FirstStatus | SecondStatus | ActiveCode |
      | Active      | Inactive     | 400        |
      | Active      | Disabled     | 200        |
      | Active      | Active       | 200        |
      | Disabled    | Disabled     | 500        |
      | Disabled    | Active       | 500        |
      | Active      | Inactive     | 400        |
      | Inactive    | Inactive     | 400        |
      | Inactive    | Active       | 400        |
      | Inactive    | Disabled     | 400        |

  @DISE-175
  Scenario: I try send post request to boarding merchant with wrong id
    Given I send request to get external merchants
    When I send post request to boarding merchant
    Then the status code should be '500'