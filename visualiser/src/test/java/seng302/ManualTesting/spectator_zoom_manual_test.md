# Spectator Zoom 
Test whether or not spectators can zoom and follow racing boats.

##### Sail Test
#####Context:
    Begin a multiplayer race.
    Once the race has entered Pre-Start, boot n visualisers. These are called spectators.
    In one of the spectators:
        1. Press Z.  
        2. select a boat (any point on the row) from the positions table on the left. 
        3. Press Z.
        4. Scroll in and out using the mouse scrollwheel.
        5. Select a different boat
        6. Perform steps 3-5.
    
#####Expected Results:
    These results correspond to the numbers in the table:
        1. Nothing should happen, as no boats are selected to follow.
        2. - 
        3. Centres on the boat that was selected in the table. Vis should track boat.
        4. Boat should zoom in and out, and the visualiser should follow the boat. 
        5. Select a different boat
        6. Should replicate 3-5.

####Testing log:

#####Test:
       
    - Date: 13/9/2017
    - Current Commit of branch: b933c6c
    - Performed By: Oliver Robb, Tim Garrod
    - Result: **Pass**

#####Test:
    
    - Date: 14/9/2017
    - Current Commit of branch: 60606ff
    - Performed By: Tim Garrod, Oliver, sha162
    - Result: **Pass**


    
