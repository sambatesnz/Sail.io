#Feature: Testing that the race can be instantiated in 3 different modes (race, agar, practice)
#
#  Scenario: Starting the race via the cli options
#    Given I want to start the race in "-mode=race" mode
#    When The server starts
#    Then It should be running in "race"
##
##
##    Examples:
##    | Cli            | Mode     |
##    |     |      |
##    | -mode=agar     | agar     |
##    | -mode=practice | practice |

Feature: Testing that when a boat rounds a mark, it's next mark will be set to the following mark in the race sequence
  Background:
    Given the race is already running

  Scenario Outline: A mark or gate is passed
    Given a boat is passing mark <MarkNumber>
    When the the boat passes that mark
    Then the boats next mark will be set to <NextMark>
    Examples:
      | MarkNumber | NextMark |
      | 1          | 2        |
      | 2          | 3        |
      | 5          | 6        |



