# The API will need to verify that each business rule could be created from a template
Feature: Create the Card Issuing Country dictionary in the Dictionary Microservice
  As a System
  I want to have the dictionary for card issuing country
  So that all card issuing country options are stored in a list

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

  @DISE-986
  Scenario: I get value from the Dictionary Service with 'Merchant' key
    Given I send request to get external merchants
    When I send get request with 'Merchant' value to check Dictionary Service
    Then the status codes of active code list should be '200'
    And all responses have 'false' values in the hasErrors fields

  @DISE-986
  Scenario: I get value from the Dictionary Service with 'Currency' key
    Given I send request to get external merchants
    When I send get request with 'Currency' value to check Dictionary Service
    Then the status codes of active code list should be '200'
    And all responses have 'false' values in the hasErrors fields

  @DISE-986
  Scenario: I get value from the Dictionary Service with 'CardType' key
    Given I send request to get external merchants
    When I send get request with 'CardType' value to check Dictionary Service
    Then the status codes of active code list should be '200'
    And all responses have 'false' values in the hasErrors fields

  @DISE-986
  Scenario: I get value from the Dictionary Service with 'Country' key
    Given I send request to get external merchants
    When I send get request with 'Country' value to check Dictionary Service
    Then the status codes of active code list should be '200'
    And all responses have 'false' values in the hasErrors fields

  @DISE-986
  Scenario: I try to get value from the Dictionary Service with wrong key
    Given I send request to get external merchants
    When I send get request with 'ForNegativeTestValue' value to check Dictionary Service
    Then the status codes of active code list should be '400'
    And all responses have 'false' values in the hasErrors fields


