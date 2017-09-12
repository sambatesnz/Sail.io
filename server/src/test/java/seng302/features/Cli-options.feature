Feature: Testing that the race can be instantiated in 3 different modes (race, agar, practice)

  Scenario Outline: Starting the race via the cli options
    Given I want to start the race in <Cli>
    When The server starts on <Port>
    Then It should be running in <Mode>

  Examples:
    | Cli              | Mode       | Port |
    | "-mode=agar"     | "agar"     | 8080 |
    | "-mode=practice" | "practice" | 8081 |
    | "-mode=race"     | "race"     | 8082 |