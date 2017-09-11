Feature: Testing that the race can be instantiated in 3 different modes (race, agar, practice)

  Scenario Outline: Starting the race via the cli options
    Given I want to start the race in <Cli>
    When The server starts
    Then It should be running in <Mode>

  Examples:
    | Cli              | Mode       |
    | "-mode=agar"     | "agar"     |
    | "-mode=practice" | "practice" |
    | "-mode=race"     | "race"     |