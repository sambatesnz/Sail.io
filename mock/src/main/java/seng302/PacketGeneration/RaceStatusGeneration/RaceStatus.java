package seng302.PacketGeneration.RaceStatusGeneration;

/**
 * Enum for Race Status as defined by AC35 specification
 */
public enum RaceStatus {
    NOT_ACTIVE(0),
    WARNING(1),
    PREP(2),
    STARTED(3),
    FINISHED(4),
    RETIRED(5),
    ABANDONED(6),
    POSTPONED(7),
    TERMINATED(8),
    START_TIME_NOT_SET(9),
    PRESTART(10);

    private int value;

    RaceStatus(int value) { this.value = value;}

    public int value() { return value; }

}
