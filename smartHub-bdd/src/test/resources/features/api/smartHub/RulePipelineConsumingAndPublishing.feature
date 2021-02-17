Feature: Rule Pipeline Consuming and Publishing

  @DISE-441 @DISE-847
  Scenario: Send a rule pipeline to Service Bus for a new merchant
#    Given I select a new Merchant Id and check that it doesn't have any Merchant Rules in Redis
    When I send a random Rule Pipeline message to Service Bus for the test merchant
    Then the Merchant Rules information is updated in Redis

  @DISE-441 @DISE-848
  Scenario: Send a rule pipeline to Service Bus for an existing merchant
    Given I select a new Merchant Id and check that it doesn't have any Merchant Rules in Redis
    When I send a random Rule Pipeline message to Service Bus for the test merchant
    And I send a random Rule Pipeline message to Service Bus for the test merchant
    Then the Merchant Rules information is updated in Redis

  @DISE-441 @DISE-849
  Scenario Outline: Send an invalid rule pipeline to Service Bus
    Given I select a new Merchant Id and check that it doesn't have any Merchant Rules in Redis
    When I send a Rule Pipeline message to Service Bus with specific values:
      | pipelineKey | <pipelineKey> |
      | merchantSet | <merchantSet> |
    Then no Merchant Rules information is saved in Redis
    Examples:
      | pipelineKey | merchantSet                                                         |
      | {NULL}      | {NULL}                                                              |
      | {NULL}      | {REGEX:([A-Za-z0-9+\/]{4})*[A-Za-z0-9+\/]{2}==\|[A-Za-z0-9+\/]{3}=} |
      | {INT:8}     | {REGEX:([A-Za-z0-9+\/]{4})*[A-Za-z0-9+\/]{2}==\|[A-Za-z0-9+\/]{3}=} |
      | {INT:8s}    | {NULL}                                                              |
      | {INT:8s}    | {INT:10}                                                            |