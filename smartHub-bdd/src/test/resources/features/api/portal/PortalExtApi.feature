# The API will need to expose endpoints to: get/update merchant info
Feature: API admin
  As an user I want to get merchants list from API
  So that I can check the response

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

  Scenario: Portal Ext API - Get Rules
    Given I retrieve all the rules for '?merchantId=0'
    Then the status code should be '200'
    And The body of respond is not empty

  Scenario: Portal Ext API - Get All Reports
    Given I make GET request to '/portalextapi/v1/api/Report/GetAllReports'
    Then the status code should be '200'
    And The body of respond is not empty
