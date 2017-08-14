#Manual Testing Document 

##### Mark Rounding Test
#####Context:
    To test that the boats are correctly credited as having passed a mark when they
    go through a gate or round a single mark.
    
    Set up a race and connect to the server. 
    
    Round a mark correctly by travelling the 
    same direction as indicated by the rounding arrow. 
    
    Attempt to round a mark incorrectly by travelling the opposite way to the 
    direction indicated by the rounding arrow.
    
    Round a gate correctly by travelling through the two marks in the same direction 
    as indicated by the rounding arrows. 
    
    Attempt to round a gate incorrectly by travelling though the two marks the 
    opposite way to the direction indicated by the rounding arrow.
    
#####Expected Results:
    For rounding the mark correctly:
    See if the next mark to travel to is updated by having the rounding arrow rotating around
    the next mark.
    
    For rounding the mark incorrectly:
    See if the mark to travel to stays the same by having the rounding arrow stay circling around
    the same mark.
    
    For rounding the gate correctly:
    See if the next mark to travel to is updated by having the rounding arrow rotating around
    the next mark.
    
    For rounding the gate incorrectly:
    See if the mark to travel to stays the same by having the rounding arrows stay circling around
    the two gate marks.

####Testing log:

#####Test:

    - Date: 14/8/2017
    - Current Commit of Branch: 56f2b20
    - Performed By: Jonathan Travaille, Abel Svoboda
    - Result: Pass