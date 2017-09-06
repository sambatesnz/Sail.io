package seng302.PacketGeneration.MarkRoundingGeneration;

/**
 * Represents the status of a boat for mark rounding messages
 */
public enum BoatStatus {
    UNKNOWN(0),
    RACING(1),
    DISQUALIFIED(2),
    WITHDRAWN(3);

    private final int status;

    BoatStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
