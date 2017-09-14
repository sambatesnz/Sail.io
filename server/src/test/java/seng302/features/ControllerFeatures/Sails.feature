Feature: Testing that when the sails in/out button is pressed by the user, the appropriate message is received
  and the boat sail status is adjusted accordingly

  Background:
    Given the race is running

  Scenario Outline: A sails in/out packet is received
    Given a boatAction packet has been received with a value of <BoatAction>
    And the boats sail status is <SailStatusBefore>
    When the received boatAction packet is processed
    Then the boats sail status is altered to <SailStatusAfter>
    Examples:
      | BoatAction | SailStatusBefore | SailStatusAfter |
      | 2          | "true"           | "false"         |
      | 2          | "false"          | "false"         |
      | 3          | "true"           | "true"          |
      | 3          | "false"          | "true"          |
