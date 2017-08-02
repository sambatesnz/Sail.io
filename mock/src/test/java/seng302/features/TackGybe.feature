Feature: Testing that when the tack/gybe button is pressed by the user, the appropriate message is received and the boat hading adjusted accordingly

  Background:
    Given the race is running

  Scenario Outline: A tack/gybe packet is received
    Given a boatAction packet is received of type <BoatAction>
    And the boats current heading is <BeforeHeading> degrees
    And the current wind direction is <WindDirection>
    When te received tack/gybe packet is processed
    Then the boats current heading is chaned to <AfterHeading> degrees
    Examples:
    | BoatAction | BeforeHeading | WindDirection | AfterHeading |
    | 4          | 180           | 60            | 300          |
    | 4          | 250           | 315           | 20           |
    | 4          | 0             | 60            | 120          |
    | 4          | 120           | 60            | 0            |
    | 4          | 350           | 50            | 110          |
    | 4          | 110           | 50            | 350          |