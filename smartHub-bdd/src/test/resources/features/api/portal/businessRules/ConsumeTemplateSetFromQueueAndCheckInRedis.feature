Feature: Cache Service consumes Rule Templates from Service Bus queue and place them into Redis
  As a Business rule expert
  I want to publish a Rule Template
  So that this template could be consumed by Cache Service and stored into Redis

  Scenario: I publish one business Rules Template
    Given I send post request to create new business rule template with fields:
      | RuleTemplateId | {UUID}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | Name           | {STRING:10}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
      | Description    | {EMPTY}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | RuleXml        | {"Elements":[{"Type":0,"Name":"_divRuleEditor0","Oper":16,"FuncType":4,"InpType":2,"CalType":9,"Value":"if"},{"Type":1,"Name":"_divRuleEditor1","Oper":0,"FuncType":4,"InpType":2,"CalType":9,"Value":"ExecutionResult.RoutingResult.SelectedMID","IsRule":false,"Max":256},{"Type":3,"Name":"_divRuleEditor2","Oper":0,"FuncType":4,"InpType":2,"CalType":9,"Value":"equal"},{"Type":4,"Name":"_divRuleEditor4","Oper":0,"FuncType":4,"InpType":1,"CalType":9,"Value":"123"},{"Type":6,"Name":"_divRuleEditor5","Oper":16,"FuncType":4,"InpType":2,"CalType":9,"Value":"then"},{"Type":17,"Name":"_divRuleEditor6","Oper":16,"FuncType":4,"InpType":2,"CalType":9,"Value":"set"},{"Type":1,"Name":"_divRuleEditor7","Oper":0,"FuncType":4,"InpType":2,"CalType":9,"Value":"ExecutionResult.RoutingResult.SelectedMID","IsRule":false,"Max":256},{"Type":17,"Name":"_divRuleEditor8","Oper":0,"FuncType":4,"InpType":2,"CalType":9,"Value":"to"},{"Type":4,"Name":"_divRuleEditor10","Oper":0,"FuncType":4,"InpType":1,"CalType":9,"Value":"123"}],"IsLoadedRuleOfEvalType":false,"Command":"ceExtract","Mode":0,"Name":"A123","SkipNameValidation":false}|
      | Type           | Routing                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | Status         | {EMPTY}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
    When I send 'valid' request to publish rule template
    Then I check Template Set stored in Redis