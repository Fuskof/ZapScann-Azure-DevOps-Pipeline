Feature: Rule Templates
  As an Admin portal user
  I want to create/manage Rule Templates

  Scenario: Search for a predefined template
    Given I login to the admin portal
    When I navigate to Rule Templates
    And I search for a rule
    Then I can see that the result was filtered correctly