package seng302.PacketGeneration.MarkRoundingGeneration;

/**
 * Represents the type of mark for a mark rounding
 */
public enum MarkType {
    UNKNOWN(0),
    ROUNDING_MARK(1),
    GATE(2);             // (windward, leeward, start, finish, etc.)

    private int type;

    MarkType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
