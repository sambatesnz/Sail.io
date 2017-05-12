package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class RaceStatusUtility {

    static int OFFSET = 1;
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


    enum BoatStatus {
        UNDEFINED('0'),
        PRESTART('1'),
        RACING('2'),
        FINISHED('3'),
        DNS('4'),         // did not start
        DNF('5'),         // did not finish
        DSQ('6'),         // disqualified
        OCS('7');         // On Course Side – across start line early

        private char value;

        BoatStatus(char value) {
            this.value = value;
        }

        public char value(){
            return value;
        }
    }

    private RaceStatusUtility() {
    }

    static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    static byte[] longToSixBytes(long value) {
        byte[] wholeArray = LEBuffer(8).putLong(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 6);

    }

    static byte[] charToOneByte(char value) {
        byte[] wholeArray = LEBuffer(2).putChar(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 1);
    }

    static byte[] intToFourBytes(int value) {
        byte[] wholeArray = LEBuffer(4).putInt(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 4);
    }

}
