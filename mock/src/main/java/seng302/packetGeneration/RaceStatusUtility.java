package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class RaceStatusUtility {

    static int MESSAGE_VERSION = 0;
    static int MESSAGE_VERSION_SIZE = 1;
    static int CURRENT_TIME = 1;
    static int CURRENT_TIME_SIZE = 6;
    static int RACE_ID = 7;
    static int RACE_ID_SIZE = 4;
    static int RACE_STATUS = 11;
    static int RACE_STATUS_SIZE = 1;
    static int EXPECTED_START_TIME =  12;
    static int EXPECTED_START_TIME_SIZE = 6;
    static int WIND_DIRECTION = 18;
    static int WIND_DIRECTION_SIZE = 2;
    static int WIND_SPEED = 20;
    static int WIND_SPEED_SIZE = 2;
    static int NUM_BOATS = 22;
    static int NUM_BOATS_SIZE = 1;
    static int RACE_TYPE = 23;
    static int RACE_TYPE_SIZE = 1;
    static int BOATS = 0;
    static int BOATS_SIZE = 20;


    private RaceStatusUtility() {

    }

    static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    static byte[] longToSixBytes(long value) {
        byte[] wholeArray = RaceStatusUtility.LEBuffer(8).putLong(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 6);

    }
}
