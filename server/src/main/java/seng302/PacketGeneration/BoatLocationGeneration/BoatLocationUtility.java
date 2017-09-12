package seng302.PacketGeneration.BoatLocationGeneration;

/**
 * Sections of the boatLocationMessage
 */
public enum BoatLocationUtility {

    MESSAGE_VERSION(0, 1),
    TIME(1, 6),
    SOURCE_ID(7, 4),
    SEQUENCE_NUMBER(11,4),
    DEVICE_TYPE(15,1),
    LATITUDE(16,4),
    LONGITUDE(20,4),
    ALTITUDE(24,4),
    HEADING(28,2),
    PITCH(30,2),
    ROLL(32,2),
    BOAT_SPEED(34,2),
    COG(36,2),
    SOG(38,2),
    APPARENT_WIND_SPEED(40,2),
    APPARENT_WIND_ANGLE(42, 2),
    TRUE_WIND_SPEED(44, 2),
    TRUE_WIND_DIRECTION(46, 2),
    TRUE_WIND_ANGLE(48, 2),
    SAIL_STATUS(50, 2),
    CURRENT_SET(52, 2),
    RUDDER_ANGLE(54, 2);

    private int index;
    private int size;

    BoatLocationUtility(int index, int size){
        this.index= index;
        this.size = size;
    }

    public int getIndex(){
        return index;
    }

    public int getSize() {
        return size;
    }
}
