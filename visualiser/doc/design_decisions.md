# Design Decisions

####05-06
Currently looking into refactoring the race controller. Have decided to refactor everything into smaller packages to separate features/concerns ot the app.

####Goals for refactoring / ReEngineering
* Make the app load components separately. Specifically App should work if it receives a course layout and not boat location messages
* Write manual tests to ensure these components work    
* Come up with single responsibility for the RaceController, subclass anything that doesn't meet this concern
* Remove update view function, separate all responsibilities of methods



##Things that need to be done
* Write a manual test for mock which creates a spinny arrow to verify that the arrow updates correctly
* Write a manual test for mock which cycles through some time zones and displays them on visualiser (It should handle it when the race isnt set)

    