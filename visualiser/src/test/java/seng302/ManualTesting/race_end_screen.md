#Manual Testing Document 

##### Race finished by all boats finishing
#####Context:
    The client should be able to see the results of the race after it is finished. 
    The race2 xml has only 1 leg for testing this without completing a whole race.
     
    - Start the server and connect 2 boats, then finish the race on both clients.
    
    
#####Expected Results:
    - Once a boat passes the finish line, they should be able to see the finished list pop up on their screen.
    - The user should be able to hide and show this screen.
    - The users and other finisihers details should be correctly recorded in this list.
    - After 10 seconds the race scene should close and be replaced with the score screen which show the same information
        as the finishing screen.
    - The user can click the menu button to return to the menu.
    

####Testing log:

#####Test:
   
    - Date: 13/09/2017
    - Current Commit of branch: 7b9ba18e09d8a0500f9b88a1bff0e37c21daf364
    - Performed By: Sam Bates
    - Result: **Pass**

##### Race finished by server closing
#####Context:
    The server is closed while a race is still running
    
     
    - Start a server and connect 2 boats
    - Close the server
     
    - Start a server and connect 2 boats
    - Finish the race on 1 boat
    - Close the server
    
    
#####Expected Results:
    - The client should close race and display the menu.
     
    - The client should close the race and display the score screen with the finished boats listed.
    
####Testing log:

#####Test:
   
    - Date: 13/09/2017
    - Current Commit of branch: 7b9ba18e09d8a0500f9b88a1bff0e37c21daf364
    - Performed By: Sam Bates
    - Result: **Pass**
