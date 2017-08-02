Feature: A users boat should shift to a heading that results in an optimal VMG when a VMG boat action is received



  Scenario Outline: A VMG packet is received
    Given a boatAction packet is received of type <BoatAction>
    And the boats current heading is <BeforeHeading> degrees
    And the current wind direction is <WindDirection>
    When the received VMG packet is processed
    Then the boats current heading is changed to <AfterHeading> degrees
  Examples:
  | BoatAction | BeforeHeading | WindDirection | AfterHeading |
  | 4          | 180           | 60            | 300          |
  | 4          | 250           | 315           | 20           |
  | 4          | 0             | 60            | 120          |
  | 4          | 120           | 60            | 0            |
  | 4          | 350           | 50            | 110          |
  | 4          | 110           | 50            | 350          |