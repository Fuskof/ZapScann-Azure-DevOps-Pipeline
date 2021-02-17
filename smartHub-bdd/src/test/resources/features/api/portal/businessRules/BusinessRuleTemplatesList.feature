# The API will need to verify that business rule list is received by request
Feature: Save and display business rule template
  As a Business Rule Expert
  I want to have a business rule template list
  So that all templates are sorted and paginated by

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

    @DISE-409
  Scenario Outline: Business rule template list is received and sorted by criteria
    Given I send get request for receive list of rule template:
      | page     | <page>     |
      | pageSize | <pageSize> |
      | sortBy   | <sortedBy> |
      | order    | <order>    |
    Then the status code should be '200'
    And business rule templates are sorted by <sortedBy> with <order> order
    And the number of templates on the page is matched to <pageSize> according to <page>
    Examples:
      | sortedBy  | order   | page | pageSize |
      | Name      | Descend | 1    | 10       |
      | Type      | Descend | 2    | 10       |
      | CreatedOn | Descend | 3    | 10       |
      | Status    | Descend | 1    | 25       |
      | Name      | Ascend  | 1    | 10       |
      | Type      | Ascend  | 1    | 10       |
      | Status    | Ascend  | 1    | 25       |
      | CreatedOn | Ascend  | 1    | 10       |

    @DISE-409
  Scenario Outline: Business rule template list is received and paginated according to pageSize
    Given I send get request for receive list of all rule templates
    When I send get request for receive list of rule template:
      | page     | X          |
      | pageSize | <pageSize> |
      | sortBy   | Name       |
      | order    | Descend    |
    Then the status code should be '200'
    And the number of templates on the page is matched to <pageSize> according to <page>
    Examples:
      | pageSize | page |
      | 10       | X    |
      | 25       | X    |
      | 50       | X    |
      | 100      | X    |

  @DISE-409
  Scenario: Business rule template list is received without sortedBy and order params
    When I send get request for receive list of rule template:
      | page     | -0 |
      | pageSize | 31 |
    Then the status code should be '200'
    And the number of templates on the page is matched to 31 according to 0

  @DISE-409
  Scenario: Business rule ALL template list is received
    Given I send get request for receive list of all rule templates
    Then the status code should be '200'

  @DISE-409
  Scenario Outline: I send request with invalid order
    Given I send get request for receive list of rule template:
      | page     | <page>     |
      | pageSize | <pageSize> |
      | sortBy   | <sortedBy> |
      | order    | <order>    |
    Then the status code should be '400'
    Examples:
      | sortedBy | order     | page | pageSize |
      | Name     | z_______W | 1    | 10       |

  @DISE-409
  Scenario Outline: I send request with invalid pageSize
    Given I send get request for receive list of rule template:
      | page     | <page>     |
      | pageSize | <pageSize> |
      | sortBy   | <sortedBy> |
      | order    | <order>    |
    Then the status code should be '400'
    Examples:
      | sortedBy | order   | page | pageSize     |
      | Name     | Descend | 1    | 100000000000 |

  @DISE-409
  Scenario Outline: I send request with invalid name of field sortedBy
    Given I send get request for receive list of rule template:
      | page     | <page>     |
      | pageSize | <pageSize> |
      | sortBy   | <sortedBy> |
      | order    | <order>    |
    Then the status code should be '200'
    And the number of templates on the page is matched to 10 according to 1
    Examples:
      | sortedBy | order   | page | pageSize |
      | NameName | Descend | 1    | 10       |