package seng302.Messages;

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
    private byte data[];
    private byte crc[];

    public Message(){}

    public Message(byte[] data){

        syncByte1 = ByteBuffer.wrap(data, 1, 1).order(ByteOrder.LITTLE_ENDIAN).getInt();
        syncByte2 = ByteBuffer.wrap(data, 2, 1).order(ByteOrder.LITTLE_ENDIAN).getInt();
        messageType = ByteBuffer.wrap(data, 3, 1).order(ByteOrder.LITTLE_ENDIAN).getInt();
        messageLen = ByteBuffer.wrap(data, 13, 2).order(ByteOrder.LITTLE_ENDIAN).getInt();
        data = new byte[messageLen];
        System.arraycopy(data,15, data,0, messageLen);
        crc = new byte[4];
        System.arraycopy(data,15 + messageLen, data,0, 4);
    }

    public void processMessage(){
        switch (messageType) {
            case 1:                                             //Heartbeat
                break;
            case 12: //race.setRaceStatus(data[11]);            //Race Status
                break;
            case 20:                                            //Display
                break;
            case 26:                                            //XML Message
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
                LocationMessage location = new LocationMessage(data);
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
