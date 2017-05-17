package seng302.Messages;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Class to read in packets from a socket stream and distribute them to their relative
 * message handler.
 */
public class Message {
    private int syncByte1;
    private int syncByte2;
    private int messageType;
    private int messageLen;
    private byte body[];
    private byte crc[];

    /**
     * Constructor for the class. Takes an array of bytes, extracts information from the header
     * (such as the message type and length) and removes it from the rest of the data,
     * along with the CRC, in preparation for the message to be given to it's relative handler.
     * @param data The array of bytes containing the header, message and CRC
     */
    public Message(byte[] data){
        syncByte1 = byteArrayToInt(data, 0, 1);
        syncByte2 = byteArrayToInt(data, 1,1);
        messageType = byteArrayToInt(data, 2,1);
        messageLen = byteArrayToInt(data, 13,2);

        body = new byte[messageLen];
        System.arraycopy(data,15, body,0, messageLen);
        crc = new byte[4];
        System.arraycopy(data,15 + messageLen, crc,0, 4);
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
    public static long byteArrayToLong(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[8];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Gives the packet to the relative message handler based on the messageType
     * @throws UnsupportedEncodingException
     */
    public void parseMessage() throws UnsupportedEncodingException {
        switch (messageType) {
            case 1:                                             //Heartbeat
                break;
            case 12:                                            //Race Status
                RaceStatusMessage raceStatus = new RaceStatusMessage(body);
                break;
            case 20:                                            //Display
                break;
            case 26:                                            //XML Message
                XMLMessage xmlMessage = new XMLMessage(body);
                break;
            case 27:                                            //Race Start Status

                break;
            case 29:                                            //Yacht Event Code
                break;
            case 31:                                            //Yacht Action Code
                break;
            case 36:                                            //Chatter Text
                break;
            case 37:                                            //Boat Location
                LocationMessage location = new LocationMessage(body);
                break;
            case 38:                                            //Mark Rounding
                break;
            case 44:                                            //Course Wind
                break;
            case 47:                                            //Avg Wind
                break;
        }
    }


}
