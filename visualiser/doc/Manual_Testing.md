#Manual Testing Document

#####Keybinding test

* Date of Testing: 18/7/17
* Commit [ 9b7a563a6b8e9e12cd86886c9f4600241a24776e ]

In order to test this feature, we have connected a client to the server
 that streams the mock race data. When a bound key is pressed, and the
 server receives the boat action packet, the server will print a number
 corresponding to the Game-X controller protocol binary message number.
 
 In order to test the program, we started the server and client, and
 input the following pattern of keypresses:
 * Page Up
 * Page Down
 * Page Up
 * Enter
 * Space
 * Enter
 * Enter
 * Shift
 * Space
 * Shift
 * Shift
 * Shift
 
 According to the Game-X controller protocol, we expected the following 
 numbers to be printed by the server:
 

 5, 6, 5, 4, 1, 4, 4, 3, 1, 2, 3, 2
 
 The results we got from the server's standard output were:
 
 5, 6, 5, 4, 1, 4, 4, 3, 1, 2, 3, 2
 
 This is what we expected, therefore our test passed.
 
 
 #####WindArrow test
 
 * Date of Testing: 24/7/17
 
 In order to test this feature, we require the mock to be set up to produce wind data 
 that exists within a set range of expected variable wind speed, with varying wind directions. 
 We expect to see the wind arrow, in a varying number of directions, and in varying scales of size, 
 that are still of a reasonable size that allows for appropriate viewing by the user.