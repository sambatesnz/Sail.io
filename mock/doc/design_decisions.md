# Design Decisions


##Binary Messages
Binary messages are generated using the template method.
Any message type can be implemented as a child class of the Binary Message class.
The template method was chosen because the header and the crc of any implementation of a message is the same.
The body of each message is different in each implemented and thus is makes sense to make that the abstract method implemented in by the child classes.The template method was chosen because sections anpit tje 


##Tacking and Gybing
Boats will smoothly turn through the tack/gybe instead of 
instantly changing their heading.
Pressing the tack/gybe button again while the boat is already
tacking/gybing will cease the current tack/gybe instead.
Turning upwind/downwind will also cease a tack/gybe if there 
is one currently in progress.