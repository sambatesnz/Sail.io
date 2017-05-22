package seng302.packetGeneration.MessageHeaderGeneration;


import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.PacketGenerationUtils;

import java.util.Arrays;

/**
 * Header of a packet as defined by the AC35 specification
 * Does not extend binary message otherwise you could create a message with a header and the body that is also the header
 */
public class MessageHeader {
    private static final byte SYNC_BYTE_1 = (byte) 0x47; //From specification
    private static final byte SYNC_BYTE_2 = (byte) 0x83;
    private static final int MESSAGE_SIZE = 15;

    private byte[] syncByte1;
    private byte[] syncByte2;
    private byte[] type;
    private byte[] timeStamp;
    private byte[] sourceId;
    private byte[] bodyLength;
    private int messageType;

    private byte[] messageHeader;

    public MessageHeader(int messageType, int sourceId, int bodyLength){
        this.syncByte1 = PacketGenerationUtils.byteToByteArray(SYNC_BYTE_1);
        this.syncByte2 = PacketGenerationUtils.byteToByteArray(SYNC_BYTE_2);
        this.type = PacketGenerationUtils.intToOneByte(messageType);
        this.timeStamp = PacketGenerationUtils.longToSixBytes(System.currentTimeMillis());
        this.sourceId = PacketGenerationUtils.intToFourBytes(sourceId);
        this.bodyLength = PacketGenerationUtils.intToTwoBytes(bodyLength);
        this.messageType = messageType;
        this.messageHeader = new byte[MESSAGE_SIZE];
    }

    public byte[] getBody() {
        int firstIndex = 0;
        System.arraycopy(syncByte1, firstIndex, messageHeader, HeaderByteValues.SYNC_BYTE_1.getIndex(), HeaderByteValues.SYNC_BYTE_1.getSize());
        System.arraycopy(syncByte2, firstIndex, messageHeader, HeaderByteValues.SYNC_BYTE_2.getIndex(), HeaderByteValues.SYNC_BYTE_2.getSize());
        System.arraycopy(type, firstIndex, messageHeader, HeaderByteValues.MESSAGE_TYPE.getIndex(), HeaderByteValues.MESSAGE_TYPE.getSize());
        System.arraycopy(timeStamp, firstIndex, messageHeader, HeaderByteValues.TIME_STAMP.getIndex(), HeaderByteValues.TIME_STAMP.getSize());
        System.arraycopy(sourceId, firstIndex, messageHeader, HeaderByteValues.SOURCE_ID.getIndex(), HeaderByteValues.SOURCE_ID.getSize());
        System.arraycopy(bodyLength, firstIndex, messageHeader, HeaderByteValues.MESSAGE_BODY_LENGTH.getIndex(), HeaderByteValues.MESSAGE_BODY_LENGTH.getSize());
        System.out.println("Message Header: +" + Arrays.toString(messageHeader));
        return messageHeader;
    }


}
