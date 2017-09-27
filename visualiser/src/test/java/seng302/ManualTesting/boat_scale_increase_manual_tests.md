#Boat Scale Testing 
Test that the boat scaling functionality after a collision works as expected

##### Boat Size Test
#####Context:
    Connect to an agar server from two visualisers. 
    Run an agar race, and collide the two boats.
    Collide the two boats again.
    
    
#####Expected Results:
    With each collision, the winning boat will increase in size. 

####Testing log:

#####Test:
   
    - Date: 25/9/2017
    - Current Commit of branch: 8ef85318c3045f6cbf2a023c5f7eb46217efc60b
    - Performed By: Matt Simpson
    - Result: Fail
    
    - Date: 25/9/2017
    - Current Commit of branch: 8ef85318c3045f6cbf2a023c5f7eb46217efc60b
    - Performed By: Matt Simpson
    - Result: Pass
    
    - Date: 26/9/2017
    - Current Commit of branch: df8d9a7b805c3924b04366fa4af0201ac7f4f380
    - Performed By: Matt Simpson
    - Result: Pass



##### Boat Collision Size Test
#####Context:
    Connect to an agar server from two visualisers.
    Run an agar race and collide the two boats twice, only allowing the same one boat to win both times.
    Before the third collision, zoom in on one of the boats and aim to make the boats collide.
    The easiest way to view how the collision functions is to get the boats travelling at similar speeds side by side 
    and turning one into the other
    
#####Expected Results:
    As the two control circles around the boats meet, the boats collide, filling the circle of the boat that visualiser
    is controlling with colour. One boat will lose a life.
    
#### Testing Log

##### Test:

    - Date: 26/9/2017
    - Current Commit of branch: df8d9a7b805c3924b04366fa4af0201ac7f4f380
    - Performed By: Matt Simpson
    - Result: Pass
    
##### Test:

    - Date: 26/9/2017
    - Current Commit of branch: df8d9a7b805c3924b04366fa4af0201ac7f4f380
    - Performed By: Tim Garrod
    - Comment: was sailing two boats over one another at different speeds without a collision occuring
    - Result: Fail    
    
##### Test:

    - Date: 26/9/2017
    - Current Commit of branch: 09127161960c7cfe58f77ad54433537a30a37264
    - Performed By: Matt Simpson
    - Result: Pass
 
    
    
    
##### Secondary Boat Collision Size Test
#####Context:
    Connect to an agar server from two visualisers.
    Restart the server, returning both visualisers to the main page.
    Restart ONE visualiser, and connect both visualisers to a new agar race.
    Collide the boats, letting the same boat win each time.
    For each collision, zoom in on one of the visualisers, and compare the 
    The easiest way to view how the collision functions is to get the boats travelling at similar speeds side by side 
    and turning one into the other
    
#####Expected Results:
    The two visualisers show the two boats and their circles meeting at the same time.
    As the two control circles around the boats meet, the boats collide, filling the circle of the boat that visualiser
    is controlling with colour. One boat will lose a life.
    
#### Testing Log

##### Test:

    - Date: 27/9/2017
    - Current Commit of branch: fd289dab934e94594feb969a78c780e0ced2232c
    - Performed By: Matt Simpson
    - Comment: No scaling of boats occurs, and no lives are lost
    - Result: Fail
    
    

    
