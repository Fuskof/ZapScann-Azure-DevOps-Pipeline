@smoke
Feature: Implement core functions of Batch Processor service
  The service accepts incoming batch files containing multiple transactions in basic format with only a few fields.
  Main purpose for this endpoint is to transform every transaction request payload into a canonical form.

  Scenario: Verify ProcessBatchFile endpoint
    Given I process the following batch file 'batch.csv'
    Then the status code should be '200'
    And the response message should be 'OK'
    And I convert the response to json for BatchProcessor