#Manual Testing Document 
- There are two cases when the visualiser should be reset: 
    
     1: A race has finished and the server tries to begin a new race
     
     2: The server disconnects 

### A finished Race
#####Context:
    Run the ServerRestartTest 
    Open two visualisers
    Connect both of the visualisers
    Wait approximately 40 seconds
    
#####Expected Results:
    The visualisers should load into a race (should be empty eg. no marks)
    After 40 seconds the visualisers should both reload into the start screen
    
    

####Testing log:

#####Test:
   
- Date: 07/09/2017
- Current Commit of branch: 20f5f98
- Performed By: sha162, osr13
- Result: **Pass**

    

    
