#Manual Testing Document 
Testing that the boats are moving 
correctly when the VMG button is pressed

##### VMG Test
#####Context:
    
    Run the server
    Connect a testClient to the server
    Press the SPACE key while facing different angles
    Press the PGUP key right after the SPACE key
    
#####Expected Results:
    The boat should move smoothly to the closest VMG angle when it's pressed  
    once outside of the deadzone.
    
    After the PGUP key is pressed, the boat should stop snapping to the  
    VMG angle and the user should be in control again.
    

####Testing log:
#####Test:
    - Date: 8/8/17
    - Commit: 078cf84
    - Performed By: Sam Bates
    - Result: Pass

######Test:
    - Date: 28/8/2017
    - Current Commit of Branch: 6b53f48
    - Performed By: Stefan hall 
    - Result: Pass

    

    
