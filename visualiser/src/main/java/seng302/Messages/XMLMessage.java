package seng302.Messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by sba136 on 11/05/17.
 */
public class XMLMessage {
    int xmlMessageSubtype;
    int xmlMessageLen;
    byte[] xmlMessage;

    public XMLMessage(byte[] data){
        xmlMessageSubtype = ByteBuffer.wrap(data, 9, 1).order(ByteOrder.LITTLE_ENDIAN).getInt();
        xmlMessageLen = ByteBuffer.wrap(data, 12, 2).order(ByteOrder.LITTLE_ENDIAN).getInt();
        xmlMessageLen = ByteBuffer.wrap(data, 12, 2).order(ByteOrder.LITTLE_ENDIAN).getInt();
        xmlMessage = new byte[xmlMessageLen];
        System.arraycopy(data,15, data,0, xmlMessageLen);


    }

}
