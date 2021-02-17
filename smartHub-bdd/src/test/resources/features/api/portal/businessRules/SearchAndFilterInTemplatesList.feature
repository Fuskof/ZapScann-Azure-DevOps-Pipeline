# The API will need to verify that business rule list is received by request
Feature: Search and filter in templates list
  As a Business Rule Expert
  I want to search and filter in a Template's list
  So that I can find a particular business rule template

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

  @DISE-449
  Scenario Outline: Business rule template list is diplayed by criteria
    Given I search for rule template  by criteria text <text> and type <type> and status <status>
    Then the status code should be '<statusCode>'
    And text '<text>', '<type>' and '<status>' is persisted in every template of response
    Examples:
      | text            | type          | status     | statusCode |
      | Rou             | Routing       | Active     | 200        |
      | Empty           | Routing       | Active     | 200        |
      | Rou             | Validation    | Draft      | 200        |
      | Empty           | MidConditions | Draft      | 200        |
      | NonExistingName | Routing       | Active     | 200        |
      | None            | None          | None       | 200        |
      | None            | None          | Active     | 200        |
      | None            | Routing       | None       | 200        |
      | Rou             | None          | None       | 200        |
      | Rou             | Empty         | Active     | 400        |
      | Rou             | Routing       | Empty      | 400        |
      | Rou             | wrongValue    | Draft      | 400        |
      | None            | MidConditions | wrongValue | 400        |
      | Rou             | MidConditions | wrongValue | 400        |
      | Rou             | wrongValue    | Active     | 400        |
      | Rou             | wrongValue    | Draft      | 400        |
      | Empty           | MidConditions | wrongValue | 400        |
      | Rou             | MidConditions | wrongValue | 400        |
      | Rou             | wrongValue    | Active     | 400        |
      | Empty           | Empty         | Empty      | 400        |
      | Empty           | Empty         | Active     | 400        |
      | Empty           | Routing       | Empty      | 400        |
      | Rou             | Empty         | Empty      | 400        |
