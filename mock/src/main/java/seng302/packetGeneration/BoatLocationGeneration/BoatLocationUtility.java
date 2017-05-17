package seng302.packetGeneration.BoatLocationGeneration;

/**
 * Sections of the boatLocationMessage
 */
public enum BoatLocationUtility {

    MESSAGE_VERSION(0, 1),
    TIME(1, 6),
    SOURCE_ID(7, 4),
    SEQUENCE_NUMBER(11,4),
    DEVICE_TYPE(15,1),
    LATITUDE(16,4), LONGITUDE(20,4);

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
