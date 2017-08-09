Feature: Sails up and down

  Scenario: Listening to pg down
    Given Iam sailing at a non zero velocity
    When I press the shift button
    And The sails are out
    Then The sails will go in
    And the boat will slow down to a stop
