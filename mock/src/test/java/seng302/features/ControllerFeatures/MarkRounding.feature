Feature: Testing that when a boat rounds a mark, it's next mark will be set to the following mark in the race sequence
  Background:
    Given the race is running

  Scenario Outline: A mark or gate is passed
    Given a boat is passing mark <MarkNumber>
    When the the boat passes that mark
    Then the boats next mark will be set to <NextMark>
    Examples:
    | MarkNumber | NextMark |
    | 0          | 1        |
    | 2          | 3        |
    | 5          | 6        |