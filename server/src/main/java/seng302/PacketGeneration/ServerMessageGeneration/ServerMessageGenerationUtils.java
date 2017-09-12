package seng302.PacketGeneration.ServerMessageGeneration;

import seng302.PacketGeneration.PacketGenerationUtils;
import seng302.PacketParsing.PacketParserUtils;

import java.util.Arrays;

/**
 * Utility for messages which are passed around on the SERVER ONLY
 */
public class ServerMessageGenerationUtils {

    private static int HEADER_SIZE = 4;
    private static int HEADER_SOURCE_INDEX = 0;

    public static byte[] wrap(byte[] data, int id) {
        int originalMessageLength = PacketParserUtils.getMessageLength(data);

        byte[] wrappedMessage = new byte[HEADER_SIZE + originalMessageLength];
        System.arraycopy(data, 0, wrappedMessage, HEADER_SIZE, originalMessageLength);

        byte[] header = PacketGenerationUtils.intToFourBytes(id);
        System.arraycopy(header, 0, wrappedMessage, HEADER_SOURCE_INDEX, HEADER_SIZE);
        return wrappedMessage;
    }

    public static int unwrapHeader(byte[] wrappedData){
        return PacketParserUtils.byteArrayToInt(wrappedData, HEADER_SOURCE_INDEX, HEADER_SIZE);
    }

    public static byte[] unwrapBody(byte[] wrappedData){
        byte[] unwrapped = new byte[wrappedData.length - HEADER_SIZE];
        System.arraycopy(wrappedData, HEADER_SIZE, unwrapped, 0, unwrapped.length);
        return unwrapped;
    }
}
