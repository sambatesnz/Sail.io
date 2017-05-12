package seng302.packetGeneration;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

/**
 * Created by tjg73 on 12/05/17.
 */
public class BoatStatusMessageTest {

    @Test
    public void testSourceId() throws Exception {
        int sourceBoatID = 3;

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                sourceBoatID,
                RaceStatusUtility.BoatStatus.FINISHED.value(),
                '1',
                0L,
                0L);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[4];
        int expectedBoatID = sliceArray(message, 0, actualMessage, 4);
        assertEquals(sourceBoatID, expectedBoatID);
    }

    @Test
    public void testBoatStatus() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    @Test
    public void testLegNumber() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    @Test
    public void testEstTimeToMark() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    @Test
    public void testEstTimeToFinish() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    private int sliceArray(byte[] message, int sourceIndex, byte[] actualMessage, int size){
        System.arraycopy(message, sourceIndex, actualMessage, 0, size);
        return ByteBuffer.wrap(actualMessage).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}