Feature: Multiple clients can connect to the same race instance from the server.
  Background:
    People want to be able to play against each other

  Scenario: 2 racers
    Given that 2 people have connected to a race
    When the race starts
    Then Each player should be given a unique source id for a boat

