Feature: Testing that when a boat goes within collision distance to another object, a collision is detected.

  Scenario: A boat is within collision distance of another boat
    Given There are 2 boats
    When the boats are within the collision range
    Then a collision will be detected

  Scenario: A boat is not within collision distance of another boat
    Given There are 2 boats
    When the boats are not within the collision range
    Then a collision will not be detected
