Feature: Modify business rule
  As a Admin
  I want to modify a business rule
  So that each business rule is up-to-date

  @DISE-611 @DISE-539 @DISE-715 @DISE-469
  Scenario: create new business rule and modify name and attribute
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-611                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And I send 'valid' request to publish rule template
    And I send request to get merchants with status 'Active' and name 'External Trader1'
    And I send request to create new rule with fields:
      | merchantId                  | reassign-from-previous-request   |
      | ruleName                    | {String:10}                      |
      | ruleTemplateId              | reassign-from-previous-request   |
      | level                       | Individual                       |
      | attributes[0].key           | RuleData.CardRuleData.Type       |
      | attributes[0].description   | Rule Card Type                   |
      | attributes[0].value         | C                                |
      | attributes[0].uiControl     | SelectorSingle                   |
      | attributes[0].valueKind     | Single                           |
      | attributes[0].arrayOfValues | {null}                           |
      | attributes[0].sourceKey     | CardType                         |
      | attributes[1].key           | RuleData.BusinessMIDs            |
      | attributes[1].description   | Business MIDs                    |
      | attributes[1].value         | {null}                           |
      | attributes[1].uiControl     | BusinessMids                     |
      | attributes[1].valueKind     | Array                            |
      | attributes[1].sourceKey     | None                             |
      | attributes[1].arrayOfValues | {array:6009673c1133c83e1d234614} |
    And I request to get pipeline with fields:
      | level      | Individual                     |
      | merchantId | reassign-from-previous-request |
    And I find id of new business rule
    When I send request to change business rule with fields:
      | ruleName                    | {String:10}                      |
      | level                       | Individual                       |
      | attributes[0].key           | RuleData.CardRuleData.Type       |
      | attributes[0].description   | Rule Card Type                   |
      | attributes[0].value         | D                                |
      | attributes[0].uiControl     | SelectorSingle                   |
      | attributes[0].valueKind     | Single                           |
      | attributes[0].arrayOfValues | {null}                           |
      | attributes[0].sourceKey     | CardType                         |
      | attributes[1].key           | RuleData.BusinessMIDs            |
      | attributes[1].description   | Business MIDs                    |
      | attributes[1].value         | {null}                           |
      | attributes[1].uiControl     | BusinessMids                     |
      | attributes[1].valueKind     | Array                            |
      | attributes[1].sourceKey     | None                             |
      | attributes[1].arrayOfValues | {array:6009673c1133c83e1d234614} |
    Then the status code should be '200'
    And new rule is present in the pipeline

  @DISE-611 @DISE-539 @DISE-469
  Scenario: create new business rule and try to modify business rule with wrong value
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-611                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And I send 'valid' request to publish rule template
    And I send request to get merchants with status 'Active' and name 'External Trader1'
    And I send request to create new rule with fields:
      | merchantId                  | reassign-from-previous-request   |
      | ruleName                    | {String:10}                      |
      | ruleTemplateId              | reassign-from-previous-request   |
      | level                       | Individual                       |
      | attributes[0].key           | RuleData.CardRuleData.Type       |
      | attributes[0].description   | Rule Card Type                   |
      | attributes[0].value         | C                                |
      | attributes[0].uiControl     | SelectorSingle                   |
      | attributes[0].valueKind     | Single                           |
      | attributes[0].arrayOfValues | {null}                           |
      | attributes[0].sourceKey     | CardType                         |
      | attributes[1].key           | RuleData.BusinessMIDs            |
      | attributes[1].description   | Business MIDs                    |
      | attributes[1].value         | {null}                           |
      | attributes[1].uiControl     | BusinessMids                     |
      | attributes[1].valueKind     | Array                            |
      | attributes[1].sourceKey     | None                             |
      | attributes[1].arrayOfValues | {array:6009673c1133c83e1d234614} |
    And I request to get pipeline with fields:
      | level      | Individual                     |
      | merchantId | reassign-from-previous-request |
    When I send request to change business rule with fields:
      | ruleId                      | WrongValue                       |
      | ruleName                    | {String:10}                      |
      | level                       | Individual                       |
      | attributes[0].key           | RuleData.CardRuleData.Type       |
      | attributes[0].description   | Rule Card Type                   |
      | attributes[0].value         | D                                |
      | attributes[0].uiControl     | SelectorSingle                   |
      | attributes[0].valueKind     | Single                           |
      | attributes[0].arrayOfValues | {null}                           |
      | attributes[0].sourceKey     | CardType                         |
      | attributes[1].key           | RuleData.BusinessMIDs            |
      | attributes[1].description   | Business MIDs                    |
      | attributes[1].value         | {null}                           |
      | attributes[1].uiControl     | BusinessMids                     |
      | attributes[1].valueKind     | Array                            |
      | attributes[1].sourceKey     | None                             |
      | attributes[1].arrayOfValues | {array:6009673c1133c83e1d234614} |
    Then the status code should be '400'