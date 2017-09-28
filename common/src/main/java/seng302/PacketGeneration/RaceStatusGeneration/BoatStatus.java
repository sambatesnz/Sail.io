package seng302.PacketGeneration.RaceStatusGeneration;

/**
 * Enum for a boats status used in packet generation.
 */
public enum BoatStatus {
    UNDEFINED(0),
    PRESTART(1),
    RACING(2),
    FINISHED(3),
    DNS(4),         // did not start
    DNF(5),         // did not finish
    DSQ(6),         // disqualified
    OCS(7);         // On Course Side – across start line early

    private int value;

    BoatStatus(int value) {
        this.value = value;
    }

    /**
     * @return a char value corresponding to the enum name
     */
    public int value(){
        return value;
    }

    public static BoatStatus getAction(int value){
        BoatStatus boatAction = UNDEFINED;
        for (BoatStatus status: BoatStatus.values()){
            if (status.value() == value) {
                boatAction = status;
            }
        }
        return boatAction;
    }
}
