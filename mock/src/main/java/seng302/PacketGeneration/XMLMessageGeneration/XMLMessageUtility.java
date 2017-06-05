package seng302.PacketGeneration.XMLMessageGeneration;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */

public enum XMLMessageUtility{
    MESSAGE_VERSION(0, 1),
    ACK_NUM(1, 2),
    TIME_STAMP(3, 6),
    XML_MESSAGE_SUB_TYPE(9, 1),
    SEQUENCE_NUMBER(10, 2),
    XML_MESSAGE_LENGTH(12, 2),
    XML_MESSAGE(14, -1);

    private int index;
    private int size;

    XMLMessageUtility(int index, int size){
        this.index= index;
        this.size = size;
    }

    public int getIndex(){
        return index;
    }

    public int getSize() {
        return size;
    }
}
