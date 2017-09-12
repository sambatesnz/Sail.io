#Manual Testing Document 

##### Boat Collision Test
#####Context:
    Testing that when the boats collide, the appropriate signals on each boat are displayed 
    To set up the test, run two client side applications and connect them to the server. When the application moves to 
    the stage in which boats can begin to move, direct one or both of the boats to meet and collide. 
    
#####Expected Results:
    When the boats collide, each client application will display a red fill in the control circle of the boat under that
    clients control for a short period of time before dissappearing. If the boats are still colliding, it will ne filled
    with the red colour after a moment.
    

####Testing log:

#####Test:
   
- Date: 10/09/2017
- Current Commit of branch: a7b04f759b0ab7014fc88ce36d45c60b5568ef5b
- Performed By: Matt Simpson
- Result: **Pass**

- Date: 12/09/2017
- Current Commit of branch: ca4993f
- Performed By: Ollie Robb, Matt Simpson
- Result: **Pass**


##### Mark Collision Test
#####Context:
    Testing that when a boat collides with a mark, the appropriate signals on the boat is displayed. 
    To set up the test, run two client side applications and connect them to the server. When the application moves to 
    the stage in which boats can begin to move, direct one of the boats to collide with a mark. 
    
#####Expected Results:
    When the boat collides with the mark, the client application in control of the boat will display a red fill in the 
    control circle of the boat under that clients control for a short period of time before dissappearing. If the boat 
    is still colliding, it will be refilled with the red colour after a moment.
    

####Testing log:

#####Test:
   
- Date: 10/09/2017
- Current Commit of branch: a7b04f759b0ab7014fc88ce36d45c60b5568ef5b
- Performed By: Matt Simpson
- Result: **Pass**

- Date: 12/09/2017
- Current Commit of branch: ca4993f
- Performed By: Ollie Robb, Matt Simpson
- Result: **Pass**

##### Boundary Collision Test
#####Context:
    Testing that when a boat collides or crosses the course boundary, the appropriate signals on the boat are displayed. 
    To set up the test, run two client side applications and connect them to the server. When the application moves to 
    the stage in which boats can begin to move, direct one of the boats to cross the course boundary. 
    
#####Expected Results:
    When the boat collides/crosses the course boundary, the client application in control of the boat will display a red
    fill in the control circle of the boat under that clients control for a short period of time before dissappearing. 
    If the boat remains outside of the course boundaries, it will be refilled with the red colour after a moment.
    

####Testing log:

#####Test:
   
- Date: 11/09/2017
- Current Commit of branch: 77a7d291c46af317fab4daaba5995545899a1411
- Performed By: Matt Simpson, Jono Travaille
- Result: **Pass** 
  
- Date: 12/09/2017
- Current Commit of branch: ca4993f
- Performed By: Ollie Robb, Matt Simpson
- Result: **Pass**
    

    
