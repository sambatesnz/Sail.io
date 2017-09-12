package seng302.PacketGeneration.BoatLocationGeneration;

/**
 * Stores the device type as an (integer from 0-13)
 * Racing yacht and unknown are only implemented but there are other fields
 */
public enum DeviceType {
    UNKNOWN(0),
    RACING_YACHT(1);

    private int type;

    DeviceType(int type){
        this.type = type;
    }

    public int getType(){ return this.type;}
}
