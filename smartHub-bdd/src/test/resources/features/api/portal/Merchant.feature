# The API will need to expose endpoints to: get/update merchant info
Feature: API admin / get all merchants list
  As an user I want to get merchants list from API
  So that I can check the response

  Scenario: Verify Admin API - Update integration manger configurations
    Given I update a Configuration with random fields
    Then the status code should be '200'
    And the body of Update Configuration response contains status 'true'
    When I retrieve a Configuration for a previously saved key
    Then the status code should be '200'
    And the response contains a configuration for that key with the status set to 'true'

  Scenario: Verify Admin API - Get configuration based on an unregistered key
    Given I retrieve a Configuration for an unregistered key '{STRING:5}'
    Then the status code should be '200'
    And the response contains a configuration for that key with the status set to 'false'