package seng302.packetGeneration.MessageHeaderGeneration;

import PacketGeneration.MessageHeaderGenerations.HeaderByteValues;
import PacketGeneration.MessageHeaderGenerations.MessageHeader;
import org.junit.Test;

import seng302.packetGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the header of a packet
 */
public class MessageHeaderTest {

    private int messageType;
    private int sourceId;
    private int bodyLength;

    private static int MESSAGE_SIZE = 15;

    private byte[] messageBody;

    public MessageHeaderTest(){
        Random generator = new Random();
        this.messageType = generator.nextInt(twoToThe(HeaderByteValues.MESSAGE_TYPE));
        this.sourceId = generator.nextInt(twoToThe(HeaderByteValues.SOURCE_ID));
        this.bodyLength = generator.nextInt(twoToThe(HeaderByteValues.MESSAGE_BODY_LENGTH));

        MessageHeader message = new MessageHeader(messageType, sourceId, bodyLength);
        messageBody = message.getBody();
    }

    @Test
    public void size() throws Exception {
        assertEquals(MESSAGE_SIZE, messageBody.length);
    }

    @Test
    public void syncByte1() throws Exception {
        int sourceIndex = HeaderByteValues.SYNC_BYTE_1.getIndex();
        int actualSyncByte1 = messageBody[sourceIndex];
        assertEquals((byte) 0x47, actualSyncByte1 );

    }

    @Test
    public void syncByte2() throws Exception {
        int sourceIndex = HeaderByteValues.SYNC_BYTE_2.getIndex();
        int actualSyncByte1 = messageBody[sourceIndex];
        assertEquals((byte) 0x83, actualSyncByte1 );

    }

    @Test
    public void type() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = HeaderByteValues.MESSAGE_TYPE.getIndex();
        int size = HeaderByteValues.MESSAGE_TYPE.getSize();
        int actualMessageType = PacketUtils.getIntFromByteArray(messageBody, sourceIndex, actualMessage, size);
        assertEquals(messageType, actualMessageType);
    }

    @Test
    public void timeStamp() throws Exception {
        long nearExpectTime = System.currentTimeMillis();
        byte[] actualMessage = new byte[8];
        int sourceIndex = HeaderByteValues.TIME_STAMP.getIndex();
        int size = HeaderByteValues.TIME_STAMP.getSize();
        long actualTimeStamp = PacketUtils.getLongFromByteArray(messageBody, sourceIndex, actualMessage, size);;
        assertEquals(nearExpectTime, actualTimeStamp, 10); //Time stamp is generated internally so we approximate it by +- 10 seconds
    }

    @Test
    public void sourceId() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = HeaderByteValues.SOURCE_ID.getIndex();
        int size = HeaderByteValues.SOURCE_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(messageBody, sourceIndex, actualMessage, size);
        assertEquals(sourceId, actualSourceId);
    }

    @Test
    public void messageLength() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = HeaderByteValues.MESSAGE_BODY_LENGTH.getIndex();
        int size = HeaderByteValues.MESSAGE_BODY_LENGTH.getSize();
        int actualMessageBodyLength = PacketUtils.getIntFromByteArray(messageBody, sourceIndex, actualMessage, size);
        assertEquals(bodyLength, actualMessageBodyLength);
    }

    /**
     * Finds 2 to the power of the size of a section of a packet
     * eg version Number is 1 bytes so it can take a max value of 2^8
     * @param value the utility value you want to find the power of
     * @return max integer that will store the amount of bytes
     */
    private int twoToThe(HeaderByteValues value){
        return (int) Math.pow(2, value.getSize() * 8);
    }

}