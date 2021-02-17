Feature: Creating MID Condition
  As a Admin
  I want to create MID Conditions
  So that I could have some conditions to the Business MIDs of a Merchant

#  Background: User is persisted in DataBase and logged in portal
#    Given User is persisted in database:
#      | username | name     |
#      | password | password |
#    And session token is generated for user

  @DISE-654
  Scenario: I send post request to creating MID Condition
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
      | description | DISE-654                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":2,\"Name\":\"_divRuleEditor1\",\"Oper\":4,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"D33EE5421CF8879F800ABCB10E2535A0\",\"IsFuncValue\":false},{\"Type\":2,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMID\"},{\"Type\":2,\"Name\":\"_divRuleEditor4\",\"Oper\":4,\"FuncType\":2,\"InpType\":2,\"CalType\":9,\"Value\":\"\",\"IsFuncValue\":false},{\"Type\":2,\"Name\":\"_divRuleEditor5\",\"Oper\":1,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CAPVolumePerMID\"},{\"Type\":2,\"Name\":\"_divRuleEditor2\",\"Oper\":4,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"D33EE5421CF8879F800ABCB10E2535A0\",\"IsFuncValue\":false},{\"Type\":15,\"Name\":\"_divRuleEditor7\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"\"},{\"Type\":3,\"Name\":\"_divRuleEditor8\",\"Oper\":4,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor9\",\"Oper\":4,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"true\"},{\"Type\":6,\"Name\":\"_divRuleEditor10\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor11\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"42AB630202893F2D82C35DBEEA21AC95\"},{\"Type\":7,\"Name\":\"_divRuleEditor13\",\"Oper\":0,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMID\"},{\"Type\":7,\"Name\":\"_divRuleEditor12\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"42AB630202893F2D82C35DBEEA21AC95\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"RRRRRRRRRRRRRRRR\",\"SkipNameValidation\":false}" |
      | type        | MidConditions                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    And I send 'valid' request to publish rule template
    Then the status code should be '200'

  @DISE-654
  Scenario: I try send post request to creating MID Condition with wrong type
    Given I send post request to create new business rule template with fields:
      | name        | {STRING:15}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
      | description | DISE-654                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
      | ruleXml     | "{\"Elements\":[{\"Type\":0,\"Name\":\"_divRuleEditor0\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"if\"},{\"Type\":2,\"Name\":\"_divRuleEditor1\",\"Oper\":4,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"D33EE5421CF8879F800ABCB10E2535A0\",\"IsFuncValue\":false},{\"Type\":2,\"Name\":\"_divRuleEditor3\",\"Oper\":0,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMID\"},{\"Type\":2,\"Name\":\"_divRuleEditor4\",\"Oper\":4,\"FuncType\":2,\"InpType\":2,\"CalType\":9,\"Value\":\"\",\"IsFuncValue\":false},{\"Type\":2,\"Name\":\"_divRuleEditor5\",\"Oper\":1,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.CAPVolumePerMID\"},{\"Type\":2,\"Name\":\"_divRuleEditor2\",\"Oper\":4,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"D33EE5421CF8879F800ABCB10E2535A0\",\"IsFuncValue\":false},{\"Type\":15,\"Name\":\"_divRuleEditor7\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"\"},{\"Type\":3,\"Name\":\"_divRuleEditor8\",\"Oper\":4,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"equal\"},{\"Type\":4,\"Name\":\"_divRuleEditor9\",\"Oper\":4,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"true\"},{\"Type\":6,\"Name\":\"_divRuleEditor10\",\"Oper\":16,\"FuncType\":4,\"InpType\":2,\"CalType\":9,\"Value\":\"then\"},{\"Type\":7,\"Name\":\"_divRuleEditor11\",\"Oper\":16,\"FuncType\":0,\"InpType\":2,\"CalType\":9,\"Value\":\"42AB630202893F2D82C35DBEEA21AC95\"},{\"Type\":7,\"Name\":\"_divRuleEditor13\",\"Oper\":0,\"FuncType\":1,\"InpType\":0,\"CalType\":9,\"Value\":\"RuleData.BusinessMID\"},{\"Type\":7,\"Name\":\"_divRuleEditor12\",\"Oper\":16,\"FuncType\":3,\"InpType\":2,\"CalType\":9,\"Value\":\"42AB630202893F2D82C35DBEEA21AC95\"}],\"IsLoadedRuleOfEvalType\":false,\"Command\":\"ceExtract\",\"Mode\":0,\"Name\":\"RRRRRRRRRRRRRRRR\",\"SkipNameValidation\":false}" |
      | type        | WrongType                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
    And I send 'valid' request to publish rule template
    Then the status code should be '500'

  @DISE-654
  Scenario: I try to send post request to creating MID Condition without attributes
    Given I send post request to create new business rule template with fields:
      | type | MidConditions |
    And I send 'valid' request to publish rule template
    Then the status code should be '500'
