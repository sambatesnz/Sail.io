Feature: Testing that when a boat goes within collision distance to another object, a collision is detected.
  Background:
    Given the race is already running

  Scenario Outline: A boat is within collision distance of another boat
    Given There are 2 boats
    When the boats are within the collision range
    Then a collision will be detected on both
    Examples:
    | MarkNumber | NextMark |
    | 1          | 2        |
    | 2          | 3        |
    | 5          | 6        |