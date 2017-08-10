package seng302.PacketParsing;

import seng302.PacketGeneration.MessageType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Parses Binary Packets
 */
public final class PacketParserUtils {

    private static final int HEADER_SIZE = 15;
    private static final int CRC_SIZE = 4;


    /**
     * Gets the message type of a binary message
     * @param data the binary message
     * @return the type of the message
     */
    public static MessageType getMessageType(byte[] data) {
        return MessageType.getType(byteArrayToInt(data, 2,1));
    }

    /**
     * Converts a section from an array of bytes into an integer.
     * @param bytes The array to convert bytes from
     * @param pos The starting index of the bytes desired to be converted
     * @param len The number of bytes to be converted (from the given index)
     * @return An integer, converted from the given bytes
     */
    public static int byteArrayToInt(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[4];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Converts a section from an array of bytes into a long.
     * @param bytes The array to convert bytes from
     * @param pos The starting index of the bytes desired to be converted
     * @param len The number of bytes to be converted (from the given index)
     * @return A long, converted from the given bytes
     */
    public static long byteArrayToLong(byte[] bytes, int pos, int len) {
        byte[] intByte = new byte[8];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }


    /**
     * Gets the first sync byte from a packet
     * @param data packet you wish to parse
     * @return sync byte
     */
    public static int getSyncByteOne(byte[] data) {
        return byteArrayToInt(data, 0, 1);
    }

    /**
     * Gets the second sync byte from a packet
     * @param data packet you wish to parse
     * @return sync byte
     */
    public static int getSyncByteTwo(byte[] data) {
        return byteArrayToInt(data, 1,1);
    }

    /**
     * Gets the length of the message body
     * @param data packet you wish to parse
     * @return sync byte
     */
    public static int getMessageBodyLength(byte[] data) {
        return byteArrayToInt(data, 13,2);
    }

    public static int getMessageLength(byte[] data) {
        return HEADER_SIZE + getMessageBodyLength(data) + CRC_SIZE;
    }
}
