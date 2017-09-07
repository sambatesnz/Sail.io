package seng302.PacketGeneration.YachtEventGeneration;

import seng302.PacketGeneration.MessageEnumUtility;

/**
 * Stores location and message
 */
public enum YachtEventUtility implements MessageEnumUtility {

    VERSION_NUMBER(0, 1),
    TIME(1, 6),
    ACKNUMBER(7, 2),
    RACE_ID(9, 4),
    DEST_SOURCE_ID(13, 4),
    INCIDENT_ID(17, 4),
    EVENT_ID(21, 1);

    private int index;
    private int size;

    YachtEventUtility(int index, int size){
        this.index = index;
        this.size = size;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getSize() {
        return size;
    }
}
