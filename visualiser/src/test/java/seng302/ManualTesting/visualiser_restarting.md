#Visualiser Restarting

#####Context:
    We  had a bug where restarting the Visualiser did not reset the 
    Coordinates that underly it. This meant in the second race, zoom was
    persisting from the first race. The game screen was also not growing upon
    entering the race.
    
    To run the test:
    
    1: Start a server and two visualisers. 
    2: Finish the race.
    3: Using the SAME visualisers, connect to another race.
    
#####Expected Results:
    Race Screen should not be zoomed in/focussed on a boat.
    Race Screen should grow to be larger than the lobby screen (roughly 1200x800)
    

####Testing log:

#####Test:
   
    - Date  20/09/17
    - Current Commit of branch:  854c334
    - Performed By: osr13
    - Result **Pass**

#####Test:
   
    - Date  20/09/17
    - Current Commit of branch:  854c334
    - Performed By: osr13, jtr44
    - Result **Pass**

