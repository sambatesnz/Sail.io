#Manual Testing Document 

##### Finished Boats Displaying Test
#####Context:
    This test is to see whether the boats show up on the finished boats list when they finish the race
    To set up the test, run a server and 3 visualisers, connect to the server and wait for the race to start.
    Have two of the boats finish the race; one finishing at a time, before bringing up the finishers list and 
    checking to see that they have been displayed as having finished.
    
    In order to perform this test, we have shortened the race to only have boats 
    round the first mark (after the start line) to be considered finished.
    
#####Expected Results:
    When the first boat rounds the finish mark, all the visualisers should display that boat as having finished.
    When the second boat rounds the finish mark, all the visualisers should display that BOTH boats as having finished.
    

####Testing log:

#####Test:
   
- Date  07/09/2017
- Current Commit of branch  9c92a3d
- Performed By: Jono
- Result **Pass**