Feature: Testing that when an downwind boat action is received the boat heading is updated away from the wind.
  Background:
    Given the race is running
    And a client is connected

  Scenario: A downwind boat action is received
    Given a boatAction packet has been received with a value of 6
    And the boats current heading is 120 degrees
    And the current wind direction is 0
    When the received boatAction packet is processed
    Then the boats current heading is altered to 117 degrees


  Scenario: A downwind boat action is received
    Given a boatAction packet has been received with a value of 6
    And the boats current heading is 320 degrees
    And the current wind direction is 0
    When the received boatAction packet is processed
    Then the boats current heading is altered to 323 degrees





