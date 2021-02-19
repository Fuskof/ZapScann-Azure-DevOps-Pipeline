# The API will need to verify that each business rule could be created from a template
@smoke
Feature: List multiselect and publish template
  As a Business rule expert
  I want to publish a single or multiple templates
  So that this template/s could be used for the creation of a business rule in a merchant's pipeline

  @DISE-500
  Scenario: I publish one business rules template
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-500                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And I send 'valid' request to publish rule template
    Then the status code should be '200'
    And business rule template is persisted in database
    And business rule status is 'Active'

  @DISE-500
  Scenario: I am trying to publish one business rules template with empty Id
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-500                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    When I send 'empty' request to publish rule template
    Then the status code should be '500'
    And business rule template is persisted in database
    And business rule status is 'Draft'

  @DISE-500
  Scenario: I am trying to publish one business rules template with wrong parameter
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-500                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    When I send 'wrong' request to publish rule template
    Then the status code should be '500'
    And business rule template is persisted in database
    And business rule status is 'Draft'

  @DISE-500
  Scenario: I try to publish the business rules template with the same name
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
      | description | DISE-611                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And I send 'valid' request to publish rule template
    Then the status code should be '200'
    Given I send post request to create new business rule template with fields:
      | name        | SAME_NAME                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
      | description | DISE-611                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":1,\"Name\":\"_divRuleEditor1\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"Transaction.Card.Type\",\"IsRule\":false,\"Max\":256},{\"Type\":3,\"Name\":\"_divRuleEditor2\",\"Oper\":0,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":4,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CardRuleData.Type\"},{\"Type\":6,\"Name\":\"_divRuleEditor4\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor5\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"},{\"Type\":7,\"Name\":\"_divRuleEditor7\",\"Oper\":8,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMIDs\"},{\"Type\":7,\"Name\":\"_divRuleEditor6\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"5CF0C6655937BADD63DC8B8C2E33EAA3\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"777999777\",\"Desc\":\"777999777\",\"SkipNameValidation\":false}" |
      | type        | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And the status code should be '400'
    And business rule template is persisted in database
    And business rule status is 'Active'
