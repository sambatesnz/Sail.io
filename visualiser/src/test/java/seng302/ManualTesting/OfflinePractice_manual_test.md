#Manual Testing Document 

##### Lone PreStart Practice
#####Context:
    The user now has the ability to practice the race prestart and race start. As this is a practice race, the user does
    not want other boats in the race at the same time, making it hard to practice the appropriate maneouvers. 
    Run the server, and then run two clients. Once the server is running, on only one client, select practice race. Then on
    the other client, enter localhost:4941 and press connect. 
    
#####Expected Results:
    The first client, the one that selected practice mode, enters the raceview as the only boat, starting with one minute
    on the race clock. The other client is unable to see the race occuring.
    

####Testing log:

#####Test:
   
    - Date: 16/08/2017
    - Current Commit of branch: a4fb73e46f923ae09987d13026944aed8bd6ea35
    - Performed By: Matt Simpson
    - Result: **Fail**
    
    - Date: 16/08/2017
    - Current Commit of branch: a4fb73e46f923ae09987d13026944aed8bd6ea35
    - Performed By: Matt Simpson
    - Result: **Pass**
    
    - Date: 17/08/2017
    - Current Commit of branch: 723501bb056cf245dbf303752cd306b2053e9309
    - Performed By: Stefan Hall
    - Result: **Pass**

    

##### Exit PreStart Practice
#####Context:
    The practice is only set up to allow the user to run the pre start and start of the race, 
    so the practice is set to end fifteen seconds after the race starts.
    
    To set up the test, run the server, and run a client. On the client, select the practice pre-start button. The 
    client will then load into the race view with only one boat, and the clock displaying at most a minute and five 
    seconds.
    
#####Expected Results:
    When the race clock reaches 0, it will begin to count up to 15, before the race stops, and the client navigates 
    back to the starting page.
    

####Testing log:

#####Test:
   
    - Date: 16/08/2017
    - Current Commit of branch: a4fb73e46f923ae09987d13026944aed8bd6ea35
    - Performed By: Matt Simpson
    - Result: **Pass**
    
    - Date: 17/08/2017
    - Current Commit of branch: 723501bb056cf245dbf303752cd306b2053e9309
    - Performed By: Stefan Hall
    - Result: **Pass**




##### Rerunning practice race
#####Context:
    The user wants to be able to restart the practice prestart after completing it once.
    To set up the test, run the server and a client. In the client, select the practice race
    option, and carry out the practice until it finishes and reverts to the starting page. Upon
    returning to the starting page, reselect the practice button.
    
#####Expected Results:
    The raceview will once again open, with only one boat, and just over a minute on the clock.
    The race will then exit and return to the starting page 15 seconds after the race starts.
    

####Testing log:

#####Test:
   
    - Date: 16/08/2017
    - Current Commit of branch: a4fb73e46f923ae09987d13026944aed8bd6ea35
    - Performed By: Matt Simpson
    - Result: **Pass**
    
    - Date: 17/08/2017
    - Current Commit of branch: 723501bb056cf245dbf303752cd306b2053e9309
    - Performed By: Stefan Hall
    - Result: **Pass**



