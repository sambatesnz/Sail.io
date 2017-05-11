package seng302;

import org.junit.Test;


import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by tjg73 on 10/05/17.
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
        int bufferStart = RaceStatusMessage.CURRENT_TIME;
        int bufferEnd = bufferStart + RaceStatusMessage.CURRENT_TIME_SIZE;
        byte[] currentTime = Arrays.copyOfRange(message, bufferStart, bufferEnd);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(currentTime);
        buffer.flip();
        buffer.rewind();
        long raceStatusTime = buffer.getLong();

        assertEquals(time, raceStatusTime);
    }
}