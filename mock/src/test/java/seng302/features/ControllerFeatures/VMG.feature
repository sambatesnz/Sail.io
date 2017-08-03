Feature: A users boat should shift to a heading that results in an optimal VMG when a VMG boat action is received
  Background:
    Given the race is running

  Scenario Outline: A VMG packet is received
    Given a boatAction packet has been received with a value of <BoatAction>
    And the boats current heading is <HeadingBefore> degrees
    And the current wind direction is <WindDirection>
    When the received boatAction packet is processed
    Then the boats current heading is altered to <HeadingAfter> degrees
  Examples:
  | BoatAction | HeadingBefore | WindDirection | HeadingAfter |
  | 1          | 180           | 60            | 165          |
  | 1          | 250           | 315           | 15           |
  | 1          | 0             | 60            | 345          |
  | 1          | 120           | 60            | 110          |
  | 1          | 350           | 50            | 110          |
  | 1          | 110           | 50            | 350          |