package seng302;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test for Message class
 */
public class MessageTest {
    @Test
    public void testGet4BytePos0() throws Exception {
        double latitude = 0.0;
        long transformed = (new Message()).get4BytePos(latitude);
        assertEquals(0, transformed);
    }

    @Test
    public void testGet4BytePos45() throws Exception {
        double latitude = 45.0;
        int transformed = (new Message()).get4BytePos(latitude);
        assertEquals(1<<29, transformed);
    }

    @Test
    public void testGet4BytePosN45() throws Exception {
        double latitude = -45.0;
        int transformed = (new Message()).get4BytePos(latitude);
        assertEquals(-(1<<29), transformed);
    }

    @Test
    public void testGet2ByteHeading90() throws Exception {
        double heading = 90.0;
        short transformed = (new Message()).get2ByteHeading(heading);
        assertEquals(1<<14, transformed);
    }

    @Test
    public void testBoatPositionBody() throws Exception {
        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
        boat.setHeading(90.0);
        byte[] message = (new Message()).boatPositionBody(boat);

        assertEquals((byte) 'U', message[7]);  // testing SourceID
        assertEquals((byte) 'S', message[8]);
        assertEquals((byte) 'A', message[9]);
        assertEquals((byte) 0x00, message[10]);

        assertEquals((byte) 0x01, message[15]);  // testing DeviceType

        // TODO: implement lat/long
//        assertEquals((byte) 0x01), message[16]);  // testing Latitude
//        assertEquals((byte) 0x01), message[17]);
//        assertEquals((byte) 0x01), message[18]);
//        assertEquals((byte) 0x01), message[19]);
//
//        assertEquals((byte) 0x01), message[20]);  // testing Longitude
//        assertEquals((byte) 0x01), message[21]);
//        assertEquals((byte) 0x01), message[22]);
//        assertEquals((byte) 0x01), message[23]);

        assertEquals((byte) 0x00, message[28]);  // testing Heading
        assertEquals((byte) 0x40, message[29]);

        assertEquals((byte) 0x32, message[34]);  // testing BoatSpeed
        assertEquals((byte) 0x82, message[35]);

        assertEquals(Message.BOAT_POSITION_LENGTH, message.length);
    }

    @Test
    public void testBoatPositionMessage() throws Exception {
        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
        Message message = new Message();
        byte[] bytes = message.boatPositionMessage(boat);
        assertEquals(Message.BOAT_POSITION_LENGTH + Message.HEADER_LENGTH + Message.CRC_LENGTH, bytes.length);

        byte[] bytesArray = new byte[bytes.length - 4];
        for (int i = 0; i < bytes.length - 4; i++) {
            bytesArray[i] = bytes[i];
        }
        byte[] realCRC = message.calculateChecksum(bytesArray);

        byte[] CRC = new byte[4];
        for (int i = 0; i < 4; i++) {
            CRC[i] = bytes[i + bytes.length - 4];
        }

        assertArrayEquals(realCRC, CRC); // testing CRC (at end of message)
    }
}
