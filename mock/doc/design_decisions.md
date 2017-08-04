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

##Mark Rounding
To tell if a boat has rounded a particular mark, we shall check the following:
An imaginary line will be drawn between and beyond the marks the boat is travelling
between (or parallel to a line midway between the marks if it is travelling between 
a gate and a single mark), and a perpendicular line across this at the location of 
the mark. In order for the mark to be considered 'passed' by the boat, the boat must
cross the perpendicular line corresponding to the side that the mark is required to
be passed on, then cross the original line on the opposite side of the mark from the 
last mark they have approached from (for gates, the boat can cross the line at either
side). After the boat has crossed these two lines, it will be considered as to have
passed the gate or mark.