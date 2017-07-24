Feature: Listening to key bindings

  Scenario: Listening to pg down
    Given I am I using the client
    When I press the pg down button
    Then A Boat Action message will Be generated
    And It will contain the number 2
