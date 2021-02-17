# The API will need to verify that each business rule could be created from a template
Feature: Add rule currency attribute
  As a Admin
  I want to have an currency attribute available in a business rule editor area
  So that I can create business rule with particular currency

  @DISE-643
  Scenario: I receive response with dictionary of countries
    Given I send request with tag 'Country' to receive dictionary of country
    Then the status code should be '200'
    And response has no errors

  @DISE-643
  Scenario: I create new rule with currency attributes to add rule to pipeline
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | description | Test Transaction Currency                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":8,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"RuleData.Currencies\",\"IsRule\":false,\"CollType\":0,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":8,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"contains\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"Transaction.Currency\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"Test Transaction Currency\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
    And I send 'valid' request to publish rule template
    And I send request to get merchants with status 'Active' and name 'External Trader1'
    When I send request to create new rule with fields:
      | merchantId                  | reassign-from-previous-request   |
      | ruleName                    | {String:10}                      |
      | ruleTemplateId              | reassign-from-previous-request   |
      | level                       | Individual                       |
      | attributes[0].key           | RuleData.Currencies              |
      | attributes[0].description   | Rule Currencies                  |
      | attributes[0].value         | null                             |
      | attributes[0].uiControl     | SelectorMultiple                 |
      | attributes[0].valueKind     | Array                            |
      | attributes[0].arrayOfValues | {array:USD,EUR}                  |
      | attributes[0].sourceKey     | Currency                         |
      | attributes[1].key           | RuleData.BusinessMIDs            |
      | attributes[1].description   | Business MIDs                    |
      | attributes[1].value         | null                             |
      | attributes[1].uiControl     | BusinessMids                     |
      | attributes[1].valueKind     | Array                            |
      | attributes[1].arrayOfValues | {array:6013e1722b07b2cd5ab634f8} |
      | attributes[1].sourceKey     | None                             |
    Then the status code should be '200'
    And new rule is present in the pipeline
