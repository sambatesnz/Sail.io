package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * A Utiltilies class for common methods found in each instance of packet generation
 */
public final class PacketGenerationUtils {
    private PacketGenerationUtils(){

    }

    /**
     * @param capacity of the buffer
     * @return a little endian ByteBuffer
     */
    public static ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] longToSixBytes(long value) {
        byte[] wholeArray = LEBuffer(8).putLong(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 6);

    }

    public static byte[] charToOneByte(char value) {
        byte[] wholeArray = LEBuffer(2).putChar(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 1);
    }

    public static byte[] intToFourBytes(int value) {
        byte[] wholeArray = LEBuffer(4).putInt(value).array();
        return Arrays.copyOfRange(wholeArray, 0, 4);
    }

    public static byte[] shortToTwoBytes(short value) {
        byte[] output = new byte[2];
        output[0] = (byte) value;
        output[1] = (byte) (value >>> 8);
        return output;
    }

    public static byte[] intToOneByte(int value) {
        byte[] output = new byte[1];
        output[0] = (byte) value;
        return output;
    }

    /**
     * Converts double into four bytes
     * @param location the location you want to conver
     * @return a byte array containing four bytes of the converted long
     */
    public static byte[] locationToFourBytes(double location) {
        int casted = (int) ((location+180)/360*(1L << 32L)-(1L << 31L));
        return intToFourBytes(casted);
    }
}
