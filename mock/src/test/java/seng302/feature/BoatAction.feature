Feature: BoatActionMessages

  Scenario: Receiving Sails In
    Given I am controlling a boat
    When I Press the "Sails in" control
    Then The boat's sails should be pulled in

