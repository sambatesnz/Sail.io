package seng302.packetGeneration.RaceStatusGeneration;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public enum RaceStatusUtility {

    MESSAGE_VERSION(0, 1),
    CURRENT_TIME(1, 6),
    RACE_ID(7, 4),
    RACE_STATUS(11, 1),
    EXPECTED_START_TIME(12, 6),
    WIND_DIRECTION(18, 2),
    WIND_SPEED(20, 2),
    NUM_BOATS(22, 1),
    RACE_TYPE(23, 1),
    BOATS(0, 20),
    SOURCE_ID(0, 4),
    BOAT_STATUS(4, 1),
    LEG_NUMBER(5, 1),
    NUMBER_PENALTIES_AWARDED(6, 1),
    NUMBER_PENALTIES_SERVED(7, 1),
    EST_TIME_AT_NEXT_MARK(8, 6),
    EST_TIME_AT_FINISH(14, 6);

    private int index;
    private int size;

    RaceStatusUtility(int index, int size){
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
