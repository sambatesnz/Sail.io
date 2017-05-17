package seng302.packetGeneration.BoatLocationGeneration;

/**
 * Sections of the boatLocationMessage
 */
public enum BoatLocationUtility {

    MESSAGE_VERSION(0, 1),
    TIME_POS(1, 6),
    SOURCE_ID(7, 4);

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
