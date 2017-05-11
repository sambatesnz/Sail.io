package seng302.packetGeneration;

import org.junit.Test;
import seng302.WindDirection;
import seng302.packetGeneration.RaceStatusMessage;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

/**
 * Test class for testing the race status messages
 */
public class RaceStatusMessageTest {


    @Test
    public void testMessagePacketSize() throws Exception {

        int headerSize = 24;
        int boatPacketSize = 20;
        int numBoats = 0;

        RaceStatusMessage raceStatusMessage =  new RaceStatusMessage(
                1,
                System.currentTimeMillis(),
                1,
                2,
                System.currentTimeMillis(),
                WindDirection.NORTH,
                15,
                numBoats,
                1,
                null
        );

        int expected =  headerSize + numBoats * boatPacketSize;
        int packetSize = raceStatusMessage.getRaceStatusMessage().length;
        assertEquals(packetSize, expected);
    }

    @Test
    public void testMessageVNumber() throws Exception {
        int versionNumber = 2;
        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                versionNumber,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION;
        int size = RaceStatusUtility.MESSAGE_VERSION_SIZE;
        long expectedTime = sliceArray(message, sourceIndex, actualMessage, size);
        assertEquals(versionNumber, expectedTime);
    }

    @Test
    public void testCurrentTime() throws Exception {
        long time = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                time,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = RaceStatusUtility.CURRENT_TIME;
        int size = RaceStatusUtility.CURRENT_TIME_SIZE;
        long expectedTime = sliceArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, expectedTime);
    }


    private long sliceArray(byte[] message, int sourceIndex, byte[] actualMessage, int size){
        System.arraycopy(message, sourceIndex, actualMessage, 0, size);
        return ByteBuffer.wrap(actualMessage).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }


}