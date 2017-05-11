package seng302.Messages;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by sba136 on 11/05/17.
 */
public class Message {
    private int syncByte1;
    private int syncByte2;
    private int messageType;
    private int messageLen;
    private byte body[];
    private byte crc[];

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

    public static int byteArrayToInt(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[4];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    public static long byteArrayToLong(byte[] bytes, int pos, int len){
        byte[] intByte = new byte[8];
        System.arraycopy(bytes, pos, intByte, 0, len);
        return ByteBuffer.wrap(intByte).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    public void parseMessage() throws UnsupportedEncodingException {
        switch (messageType) {
            case 1:                                             //Heartbeat
                break;
            case 12:
                RaceStatusMessage raceStatus = new RaceStatusMessage(body);            //Race Status
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
