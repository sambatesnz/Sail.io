package seng302.PacketGeneration.MarkRoundingGeneration;

/**
 * Represents the rounding side of the mark for MarkRounding messages
 */
public enum RoundingSide {
    UNKNOWN(0),
    PORT(1),
    STARBOARD(2);

    private final int side;

    RoundingSide(int side){
        this.side = side;
    }

    public int getSide() {
        return side;
    }
}
