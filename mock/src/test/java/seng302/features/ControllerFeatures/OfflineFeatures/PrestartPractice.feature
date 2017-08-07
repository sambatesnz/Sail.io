Feature: If the user wants to practice their race start, it can be done online

  Scenario: Gerald wants to practice his race starts
    Given Gerald has a client running
    When Gerald selects the practice race start option
    Then the client runs an instance of the race server on the local computer
    And the client connects with it using a message stating that it is entering practice mode
    And the client loads in the practice race scenario