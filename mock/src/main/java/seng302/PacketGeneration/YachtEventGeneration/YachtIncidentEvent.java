package seng302.PacketGeneration.YachtEventGeneration;

/**
 * Status codes for yacht incident events
 */
public enum YachtIncidentEvent {
    DEFAULT(0),
    FINISHED(11),
    BOATINBOATCOLLISION(33),
    BOATINMARKCOLLISION(35);

    private int value;

    YachtIncidentEvent(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}
