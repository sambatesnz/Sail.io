package seng302.PacketGeneration.MarkRoundingGeneration;

import org.junit.Test;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketUtils;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Tests for getting sections out of a markRoundingMessage
 */
public class MarkRoundingMessageTest {

    private int versionNumber;
    private long time;
    private int ackNum;
    private int raceID;
    private int sourceID;
    private int boatStatus;
    private int roundingSide;
    private int markType;
    private int markID;

    private MarkRoundingMessage markRoundingMessage;
    private byte[] message;

    public MarkRoundingMessageTest() {
        Random generator = new Random();
        this.versionNumber = generator.nextInt(twoToThe(MarkRoundingUtility.MESSAGE_VERSION));
        this.time = System.currentTimeMillis();
        this.ackNum = generator.nextInt(twoToThe(MarkRoundingUtility.ACK_NUMBER));
        this.raceID = generator.nextInt();
        this.sourceID = generator.nextInt();
        this.boatStatus = BoatStatus.values()[generator.nextInt(4)].getStatus();
        this.roundingSide = RoundingSide.values()[generator.nextInt(3)].getSide();
        this.markType = MarkType.values()[generator.nextInt(3)].getType();
        this.markID = generator.nextInt(twoToThe(MarkRoundingUtility.MARk_ID));

        this.markRoundingMessage = new MarkRoundingMessage(versionNumber, time, ackNum, raceID, sourceID, boatStatus,
                roundingSide, markType, markID);

        this.message = markRoundingMessage.getBody();
    }

    /**
     * Finds 2 to the power of the size of a section of a packet
     * eg version Number is 1 bytes so it can take a max value of 2^8
     * @param value the utility value you want to find the power of
     * @return max integer that will store the amount of bytes
     */
    private int twoToThe(MarkRoundingUtility value){
        return (int) Math.pow(2, value.getSize() * 8);
    }

    @Test
    public void testMessagePacketSize() throws Exception {
        int packetSize = 21; //As defined by specification
        int actualPacketSize = this.message.length;
        assertEquals(packetSize, actualPacketSize);
    }

    @Test
    public void testMessageType() throws Exception {
        int expectedMessageType = MessageType.MARK_ROUNDING.getMessageType();
        assertEquals(expectedMessageType, markRoundingMessage.getMessageType());
    }

    @Test
    public void messageVersionNumber() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.MESSAGE_VERSION.getIndex();
        int size = MarkRoundingUtility.MESSAGE_VERSION.getSize();
        int actualVersionNumber = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(versionNumber, actualVersionNumber);
    }

    @Test
    public void time() throws Exception{
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.TIME.getIndex();
        int size = MarkRoundingUtility.TIME.getSize();
        long actualTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, actualTime);
    }

    @Test
    public void ackNum() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.ACK_NUMBER.getIndex();
        int size = MarkRoundingUtility.ACK_NUMBER.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(ackNum, actualSourceId);
    }

    @Test
    public void raceId() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.RACE_ID.getIndex();
        int size = MarkRoundingUtility.RACE_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceID, actualSourceId);
    }

    @Test
    public void sourceId() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.SOURCE_ID.getIndex();
        int size = MarkRoundingUtility.SOURCE_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(sourceID, actualSourceId);
    }

    @Test
    public void boatStatus() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.BOAT_STATUS.getIndex();
        int size = MarkRoundingUtility.BOAT_STATUS.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(boatStatus, actualSourceId);
    }

    @Test
    public void roundingSide() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.ROUNDING_SIDE.getIndex();
        int size = MarkRoundingUtility.ROUNDING_SIDE.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(roundingSide, actualSourceId);
    }

    @Test
    public void markType() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.MARK_TYPE.getIndex();
        int size = MarkRoundingUtility.MARK_TYPE.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(markType, actualSourceId);
    }

    @Test
    public void markID() throws Exception {
        byte[] actualMessage = new byte[8];
        int sourceIndex = MarkRoundingUtility.MARk_ID.getIndex();
        int size = MarkRoundingUtility.MARk_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(markID, actualSourceId);
    }
}