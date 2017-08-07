# Design Decisions


##Binary Messages
Binary messages are generated using the template method.
Any message type can be implemented as a child class of the Binary Message class.
The template method was chosen because the header and the crc of any implementation of a message is the same.
The packet of each message is different in each implemented and thus is makes sense to make that the abstract method implemented in by the child classes.The template method was chosen because sections anpit tje 
