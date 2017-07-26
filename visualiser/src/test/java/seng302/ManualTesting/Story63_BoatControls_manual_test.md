#Manual Testing Document 


##### KeyBinding Manual Test
#####Context:
    Need to verify that the keypresses that we execute are represented correctly on the screen. 
    
    Connect to the server (called Mock currently, but this will likely change).
    Zoom in on the boat in the circle.
    Press "Page Up" and view the change on the boat's heading.
    Similarly for "Page Down". 
       
    
#####Expected Results:
    After "Page Up" press:
         - the boat should turn "Upwind", i.e. should align itself to be pointing in the same direction as the wind direction arrow.
    After "Page Down" press:
         - Similar to the respone for Page Up, except the boat should align away from the wind arrow.

####Testing log:

#####Test:
   
- Date: 26/07/17
- Current Commit of branch: [23d73534ccc3134c442d9615d0c2a8268d26b104]
- Performed By: osr13 and msi52
- Result: **pass**