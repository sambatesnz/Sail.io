package seng302.packetGeneration.BoatLocationGeneration;

import javafx.scene.paint.Color;
import org.junit.Test;
import seng302.Boat;
import seng302.packetGeneration.PacketUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for BoatLocationMessageDeprecated class
 */
public class BoatLocationMessageTest {

    short ZERO = 0;

//    @Test
//    public void testBoatPositionMessage() throws Exception {
//        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
//        BoatLocationMessageDeprecated boatLocationMessage = new BoatLocationMessageDeprecated();
//        byte[] bytes = boatLocationMessage.boatPositionMessage(boat);
//        assertEquals(BoatLocationMessageDeprecated.BOAT_POSITION_LENGTH + BoatLocationMessageDeprecated.HEADER_LENGTH + BoatLocationMessageDeprecated.CRC_LENGTH, bytes.length);
//
//        byte[] bytesArray = new byte[bytes.length - 4];
//        for (int i = 0; i < bytes.length - 4; i++) {
//            bytesArray[i] = bytes[i];
//        }
//        byte[] realCRC = boatLocationMessage.calculateChecksum(bytesArray);
//
//        byte[] CRC = new byte[4];
//        for (int i = 0; i < 4; i++) {
//            CRC[i] = bytes[i + bytes.length - 4];
//        }
//
//        assertArrayEquals(realCRC, CRC); // testing CRC (at end of boatLocationMessage)
//    }

    @Test
    public void t() throws Exception {
        assertTrue(false); //Not implemented yet
    }

    @Test
    public void testMessagePacketSize() throws Exception {
        int packetSize = 56; //As defined by specification

        BoatLocationMessage  boatLocationMessage = new BoatLocationMessage(
                0, 0, 0,
                0,0,0,
                0,0,ZERO,
                0,0,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO
        );

        int actualPacketSize = boatLocationMessage.getBoatLocationMessage().length;
        assertEquals(packetSize, actualPacketSize);
    }

    @Test
    public void messageVersionNumber() throws Exception {
        int versionNumber = 5;
        BoatLocationMessage  boatLocationMessage = new BoatLocationMessage(
                versionNumber, 0, 0,
                0,0,0,
                0,0,ZERO,
                0,0,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO
        );

        byte[] message = boatLocationMessage.getBoatLocationMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.MESSAGE_VERSION.getIndex();
        int size = BoatLocationUtility.MESSAGE_VERSION.getSize();
        int actualVersionNumber = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);

        assertEquals(versionNumber, actualVersionNumber);
    }

    @Test
    public void time() throws Exception {
        long time = System.currentTimeMillis();
        BoatLocationMessage  boatLocationMessage = new BoatLocationMessage(
                0, time, 0,
                0,0,0,
                0,0,ZERO,
                0,0,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO
        );

        byte[] message = boatLocationMessage.getBoatLocationMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.TIME_POS.getIndex();
        int size = BoatLocationUtility.TIME_POS.getSize();
        long actualVersionNumber = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, actualVersionNumber);
    }

    @Test
    public void sourceId() throws Exception {
        int sourceId = 43;
        BoatLocationMessage  boatLocationMessage = new BoatLocationMessage(
                0, 0, sourceId,
                0,0,0,
                0,0,ZERO,
                0,0,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO,ZERO,ZERO,
                ZERO
        );

        byte[] message = boatLocationMessage.getBoatLocationMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = BoatLocationUtility.SOURCE_ID.getIndex();
        int size = BoatLocationUtility.SOURCE_ID.getSize();
        int actualSourceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);

        assertEquals(sourceId, actualSourceId);
    }


}
