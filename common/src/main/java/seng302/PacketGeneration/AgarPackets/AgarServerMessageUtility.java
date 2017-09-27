package seng302.PacketGeneration.AgarPackets;

import seng302.PacketGeneration.MessageEnumUtility;

/**
 * Index and sizes of Agar messages
 */
public enum AgarServerMessageUtility implements MessageEnumUtility{

    BOAT_SOURCE_ID(0, 4),
    LIVES(4, 4),
    BOAT_SIZE(8, 4);

    private int index;
    private int size;

    AgarServerMessageUtility(int index, int size){
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
