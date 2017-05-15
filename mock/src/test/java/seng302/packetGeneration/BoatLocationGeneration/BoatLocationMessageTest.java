package seng302.packetGeneration.BoatLocationGeneration;

import javafx.scene.paint.Color;
import org.junit.Test;
import seng302.Boat;
import seng302.packetGeneration.BoatLocationGeneration.BoatLocationMessage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test for BoatLocationMessage class
 */
public class BoatLocationMessageTest {


    @Test
    public void testBoatPositionMessage() throws Exception {
        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
        BoatLocationMessage boatLocationMessage = new BoatLocationMessage();
        byte[] bytes = boatLocationMessage.boatPositionMessage(boat);
        assertEquals(BoatLocationMessage.BOAT_POSITION_LENGTH + BoatLocationMessage.HEADER_LENGTH + BoatLocationMessage.CRC_LENGTH, bytes.length);

        byte[] bytesArray = new byte[bytes.length - 4];
        for (int i = 0; i < bytes.length - 4; i++) {
            bytesArray[i] = bytes[i];
        }
        byte[] realCRC = boatLocationMessage.calculateChecksum(bytesArray);

        byte[] CRC = new byte[4];
        for (int i = 0; i < 4; i++) {
            CRC[i] = bytes[i + bytes.length - 4];
        }

        assertArrayEquals(realCRC, CRC); // testing CRC (at end of boatLocationMessage)
    }
}
