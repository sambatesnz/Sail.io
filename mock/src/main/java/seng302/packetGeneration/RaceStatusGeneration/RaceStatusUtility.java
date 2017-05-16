package seng302.packetGeneration.RaceStatusGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class RaceStatusUtility {

    static int OFFSET = 1;
    static int MESSAGE_VERSION_POS = 0;
    static int MESSAGE_VERSION_SIZE = 1;
    static int CURRENT_TIME_POS = 1;
    static int CURRENT_TIME_SIZE = 6;
    static int RACE_ID_POS = 7;
    static int RACE_ID_SIZE = 4;
    static int RACE_STATUS_POS = 11;
    static int RACE_STATUS_SIZE = 1;
    static int EXPECTED_START_TIME_POS =  12;
    static int EXPECTED_START_TIME_SIZE = 6;
    static int WIND_DIRECTION_POS = 18;
    static int WIND_DIRECTION_SIZE = 2;
    static int WIND_SPEED_POS = 20;
    static int WIND_SPEED_SIZE = 2;
    static int NUM_BOATS_POS = 22;
    static int NUM_BOATS_SIZE = 1;
    static int RACE_TYPE_POS = 23;
    static int RACE_TYPE_SIZE = 1;
    static int BOATS_POS = 0;
    static int BOATS_SIZE = 20;

    static int SOURCE_ID_INDEX = 0;
    static int BOAT_STATUS_INDEX = 4;
    static int LEG_NUMBER_INDEX = 5;
    static int NUMBER_PENALTIES_AWARDED_INDEX = 6;
    static int NUMBER_PENALTIES_SERVED_INDEX = 7;
    static int EST_TIME_AT_NEXT_MARK_INDEX = 8;
    static int EST_TIME_AT_FINISH_INDEX = 14;



}
