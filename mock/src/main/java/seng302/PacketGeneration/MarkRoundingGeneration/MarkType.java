package seng302.PacketGeneration.MarkRoundingGeneration;

/**
 * Created by tjg73 on 13/08/17.
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
