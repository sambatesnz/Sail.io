package seng302.PacketGeneration.MarkRoundingGeneration;

/**
 * Sections of the MarkRoundingMessage
 */
public enum MarkRoundingUtility {
    MESSAGE_VERSION(0, 1),
    TIME(1, 6),
    ACK_NUMBER(7, 2),
    RACE_ID(9,4),
    SOURCE_ID(13, 4),
    BOAT_STATUS(17, 1),
    ROUNDING_SIDE(18, 1),
    MARK_TYPE(19, 1),
    MARk_ID(20, 1);

    private int index;
    private int size;

    MarkRoundingUtility(int index, int size){
        this.index = index;
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }
}
