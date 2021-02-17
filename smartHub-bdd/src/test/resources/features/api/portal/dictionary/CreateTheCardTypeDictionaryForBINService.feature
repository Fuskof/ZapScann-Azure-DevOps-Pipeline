# The API will need to verify that each business rule could be created from a template
Feature: Create the Card Issuing Country dictionary in the Dictionary Microservice
  As a System
  I want to have the dictionary for card type
  So that all card type options are stored in a list

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

  @DISE-989
  Scenario: I receive response with dictionary of card types
    Given I send request with tag 'CardType' to receive dictionary of country
    Then the status code should be '200'
    And response has no errors