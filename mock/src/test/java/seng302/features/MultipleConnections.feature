Feature: Multiple clients can connect to the same race instance from the server.
  Background:
    People want to be able to play against each other

  Scenario: 2 racers
    Given that 2 people have connected to a race
    When the race starts
    Then Each player should be given a unique source id for a boat

  Scenario: The elusive 21st connection
    Given The maximum number of people in a race is 20
    When 1 person tries to participate in the race
    Then There should still only be 20 people in the race

#  # TODO Implement the functionality for this test
#  Scenario: Trying to connect to a race that is in prestart (1min before)
#    Given There is currently 3 people connected
#    And That race has less than 1 minute until it starts
#    When there is 1 person trying to participate
#    Then the race should only contain 3 boats


