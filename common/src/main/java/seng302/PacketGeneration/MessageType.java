package seng302.PacketGeneration;

/**
 * Message Type designators as defined by the AC35 specfication
 */
public enum MessageType {
    HEART_BEAT(1),
    RACE_STATUS(12),
    DISPLAY_TEXT_MESSAGE(20),
    XML_MESSAGE(26),
    RACE_START_STATUS(27),
    CHATTER_TEXT(36),
    BOAT_LOCATION(37),
    RACE_REGISTRATION(55),
    PARTICIPANT_CONFIRMATION(56),
    BOAT_ACTION(100);

    private int messageType;

    MessageType(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType(){return this.messageType;}

}
