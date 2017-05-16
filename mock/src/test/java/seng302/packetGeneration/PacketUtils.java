package seng302.packetGeneration;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Utility functions for tests
 */
public final class PacketUtils {

    private PacketUtils() {
    }

    /**
     * Get an int out of a byte[]
     * @param message byte array to be sliced
     * @param sourceIndex to slice from
     * @param actualMessage output array to copy to
     * @param size of message to slice out
     * @return an int from the message
     */
    public static int getIntFromByteArray(byte[] message, int sourceIndex, byte[] actualMessage, int size){
        System.arraycopy(message, sourceIndex, actualMessage, 0, size);
        return ByteBuffer.wrap(actualMessage).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Get a char out of a byte[]
     * @param message byte array to be sliced
     * @param sourceIndex to slice from
     * @param actualMessage output array to copy to
     * @param size of message to slice out
     * @return a char from the message
     */
    public static char getCharFromByteArray(byte[] message, int sourceIndex, byte[] actualMessage, int size){
        System.arraycopy(message, sourceIndex, actualMessage, 0, size);
        return ByteBuffer.wrap(actualMessage).order(ByteOrder.LITTLE_ENDIAN).getChar();
    }

    /**
     * Get a long out of a byte[]
     * @param message byte array to be sliced
     * @param sourceIndex to slice from
     * @param actualMessage output array to copy to
     * @param size of message to slice out
     * @return a long from the message
     */
    public static long getLongFromByteArray(byte[] message, int sourceIndex, byte[] actualMessage, int size){
        System.arraycopy(message, sourceIndex, actualMessage, 0, size);
        return ByteBuffer.wrap(actualMessage).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

}
