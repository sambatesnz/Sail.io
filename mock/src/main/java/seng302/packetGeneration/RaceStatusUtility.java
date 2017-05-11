package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class RaceStatusUtility {

    static int OFFSET = 1;
    static int MESSAGE_VERSION = 0;
    static int MESSAGE_VERSION_SIZE = 1;
    static int CURRENT_TIME = MESSAGE_VERSION + OFFSET;
    static int CURRENT_TIME_SIZE = 6;
    static int RACE_ID = CURRENT_TIME + OFFSET;
    static int RACE_ID_SIZE = 4;
    static int RACE_STATUS = RACE_ID + OFFSET;
    static int RACE_STATUS_SIZE = 1;
    static int EXPECTED_START_TIME = RACE_STATUS + OFFSET;
    static int EXPECTED_START_TIME_SIZE = 6;
    static int WIND_DIRECTION = EXPECTED_START_TIME + OFFSET;
    static int WIND_DIRECTION_SIZE = 2;
    static int WIND_SPEED = WIND_DIRECTION + OFFSET;
    static int WIND_SPEED_SIZE = 2;
    static int NUM_BOATS = WIND_SPEED + OFFSET;
    static int NUM_BOATS_SIZE = 1;
    static int BOATS = NUM_BOATS + OFFSET;
    static int BOATS_SIZE = 20;

    private RaceStatusUtility() {

    }

    static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }
}
