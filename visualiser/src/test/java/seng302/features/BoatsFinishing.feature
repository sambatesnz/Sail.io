Feature: Testing that when a boat finishes, it will be added to the list of finished boats
  Background:
    Given the race is already running

  Scenario Outline: Boats finishing the race
    Given there are <NumberOfBoats> boats in the race
    And  <InitialFinishedBoats> boats have finished the race
    When <FinishingBoats> more boats finish the race after the initial <InitialFinishedBoats> boats
    Then there will be <TotalFinishedBoats> boats listed as having finished
    Examples:
      | NumberOfBoats | InitialFinishedBoats | FinishingBoats | TotalFinishedBoats |
      | 3             | 0                    | 1              | 1                  |
      | 4             | 1                    | 2              | 3                  |
      | 7             | 4                    | 0              | 4                  |