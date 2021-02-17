# The API will need to expose endpoints to: /api/RulesConfiguration/ info
Feature: Rules configuration
  As an user I want to get merchants list from API
  So that I can check the response

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user
  @DISE-394
  Scenario: Save a rule by Rule Id
    Given I save a rule with specific fields:
      | merchantId | {STRING:5} |
      |  globalId  |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'

  @DISE-394
  Scenario: Save a rule by Global Id
    Given I save a rule with specific fields:
      | merchantId |   {EMPTY}  |
      |  globalId  | {STRING:5} |
    Then the status code should be '200'
    And the body of the response contains 'true'

  @DISE-394
  Scenario: Save a rule with both Global Id and Rule Id
    Given I save a rule with specific fields:
      | merchantId | {STRING:5} |
      |  globalId  | {STRING:5} |
    Then the status code should be '200'
    And the body of the response contains 'false'

  @DISE-394
  Scenario: Save a rule with empty Global Id and Merchant Id
    Given I save a rule with specific fields:
      | merchantId | {EMPTY} |
      |  globalId  | {EMPTY} |
    Then the status code should be '200'
    And the body of the response contains 'false'

  @DISE-394
  Scenario: Update Rule for the same merchant Id
    Given I save a rule with specific fields:
      | merchantId | {STRING:5} |
      |  globalId  |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'
    When I update the Rule of the previously saved merchant Id
      | globalId |   {EMPTY}  |
    Then the body of the response contains 'true'

  @DISE-394
  Scenario: Update Rule for the same global Id
    Given I save a rule with specific fields:
      |  globalId  | {STRING:5} |
      | merchantId |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'
    When I update the Rule of the previously saved global Id
      | merchantId |   {EMPTY}  |
    Then the body of the response contains 'true'

  @DISE-394
  Scenario: Get rule by merchant Id
    Given I save a rule with specific fields:
      | merchantId | {STRING:5} |
      |  globalId  |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'
    When I retrieve the rule for the previously used 'merchantId'
    Then the status code should be '200'
    And the response contains the previously used 'merchantId'

  @DISE-394
  Scenario: Get rule by global Id
    Given I save a rule with specific fields:
      |  globalId  | {STRING:5} |
      | merchantId |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'
    When I retrieve the rule for the previously used 'globalId'
    Then the status code should be '200'
    And the response contains the previously used 'globalId'

  @DISE-394
  Scenario: Publish a rule
    Given I save a rule with specific fields:
      |  globalId  | {STRING:5} |
      | merchantId |   {EMPTY}  |
    Then the status code should be '200'
    And the body of the response contains 'true'
    When I publish the rule
    Then the status code should be '200'
    And the body of the response contains 'true'