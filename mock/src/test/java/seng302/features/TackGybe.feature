Feature: Testing that when the tack/gybe button is pressed by the user, the appropriate message is received and the boat hading adjusted accordingly

  Background:
    Given the race is running

  Scenario Outline: A tack/gybe packet is received
    Given a boatAction packet has been received with a value of <BoatAction>
    And the boats current heading is <BeforeHeading> degrees
    And the current wind direction is <WindDirection>
    When the received boatAction packet is processed
    Then the boats final heading is altered to <HeadingAfter> degrees
    Examples:
    | BoatAction | BeforeHeading | WindDirection | HeadingAfter |
    | 4          | 180           | 60            | 300          |
    | 4          | 250           | 315           | 20           |
    | 4          | 0             | 60            | 120          |
    | 4          | 120           | 60            | 0            |
    | 4          | 350           | 50            | 110          |
    | 4          | 110           | 50            | 350          |