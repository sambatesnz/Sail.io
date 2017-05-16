package seng302.packetGeneration.XMLMessageGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Utility Class for storing common functions
 * Used to generate packets/byte[array]
 */
public final class XMLMessageUtility {

    static int MESSAGE_VERSION_POS = 0;
    static int MESSAGE_VERSION_SIZE = 1;
    static int ACK_NUM_POS = 1;
    static int ACK_NUM_SIZE = 2;
    static int TIME_STAMP_POS = 3;
    static int TIME_STAMP_SIZE = 6;
    static int XML_MESSAGE_SUB_TYPE_POS = 9;
    static int XML_MESSAGE_SUB_TYPE_SIZE = 1;
    static int SEQUENCE_NUMBER_POS = 10;
    static int SEQUENCE_NUMBER_SIZE = 2;
    static int XML_MESSAGE_LENGTH_POS = 12;
    static int XML_MESSAGE_LENGTH_SIZE = 2;
    static int XML_MESSAGE_POS = 14;
    static int XML_MESSAGE_SIZE;


    private XMLMessageUtility() {
    }

    /**
     * @param capacity of the buffer
     * @return a little endian ByteBuffer
     */
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

    static byte[] shortToTwoBytes(short value) {
        byte[] output = new byte[2];
        output[0] = (byte) value;
        output[1] = (byte) (value >>> 8);
        return output;
    }

    static byte[] intToOneByte(int value) {
        byte[] output = new byte[1];
        output[0] = (byte) value;
        return output;
    }
}
