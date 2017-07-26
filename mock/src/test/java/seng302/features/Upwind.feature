Feature: Testing that when an upwind boat action is received the boat heading is updated towards the wind.
  Background:
    Given the race is running

  Scenario Outline: An upwind boat action is received
    Given a boatAction packet has been received with a value of <BoatAction>
    And the boats current heading is <HeadingBefore> degrees
    And the current wind direction is <WindDirection>
    When the received boatAction packet is processed
    Then the boats current heading is altered to <HeadingAfter> degrees
    Examples:
    | BoatAction | HeadingBefore | WindDirection | HeadingAfter |
    | 5          | 120           | 0             | 117          |
    | 5          | 320           | 0             | 323          |
    | 2          | 120           | 50            | 120          |








