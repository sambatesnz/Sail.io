Feature: Testing that when a boat goes within collision distance to another object, a collision is detected.
  Background:
    Given the race is already running

  Scenario Outline: A boat is within collision distance of another boat
    Given there are 2 boats in the race
    When 1 boat comes into collision distance of the other boat
    Then a collision will be detected on both
    Examples:
    | MarkNumber | NextMark |
    | 1          | 2        |
    | 2          | 3        |
    | 5          | 6        |