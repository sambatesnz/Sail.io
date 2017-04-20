package seng302;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

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
        List<Byte> message = (new Message()).boatPositionBody(boat);

        assertEquals(new Byte((byte) 'U'), message.get(7));  // testing SourceID
        assertEquals(new Byte((byte) 'S'), message.get(8));
        assertEquals(new Byte((byte) 'A'), message.get(9));
        assertEquals(new Byte((byte) 0x00), message.get(10));

        assertEquals(new Byte((byte) 0x01), message.get(15));  // testing DeviceType

        // TODO: implement lat/long
//        assertEquals(new Byte((byte) 0x01), message.get(16));  // testing Latitude
//        assertEquals(new Byte((byte) 0x01), message.get(17));
//        assertEquals(new Byte((byte) 0x01), message.get(18));
//        assertEquals(new Byte((byte) 0x01), message.get(19));
//
//        assertEquals(new Byte((byte) 0x01), message.get(20));  // testing Longitude
//        assertEquals(new Byte((byte) 0x01), message.get(21));
//        assertEquals(new Byte((byte) 0x01), message.get(22));
//        assertEquals(new Byte((byte) 0x01), message.get(23));

        assertEquals(new Byte((byte) 0x40), message.get(28));  // testing Heading
        assertEquals(new Byte((byte) 0x00), message.get(29));

        assertEquals(new Byte((byte) 0x82), message.get(34));  // testing BoatSpeed
        assertEquals(new Byte((byte) 0x32), message.get(35));

        assertEquals(Message.BOAT_POSITION_LENGTH, message.size());
    }

    @Test
    public void testBoatPositionMessage() throws Exception {
        Boat boat = new Boat("Boat1", 33.33, Color.BLUE, "USA");
        List<Byte> message = (new Message()).boatPositionMessage(boat);
        assertEquals(Message.BOAT_POSITION_LENGTH + Message.HEADER_LENGTH + Message.CRC_LENGTH, message.size());

        CRC32 crc32 = new CRC32();
        byte[] bytesArray = new byte[message.size() - 4];
        for (int i = 0; i < message.size() - 4; i++) {
            bytesArray[i] = message.get(i);
        }
        crc32.update(bytesArray);
        byte[] realCRC = ByteBuffer.allocate(4).putInt((int) crc32.getValue()).array();

        byte[] CRC = new byte[4];
        for (int i = 0; i < 4; i++) {
            CRC[i] = message.get(i + message.size() - 4);
        }

        assertArrayEquals(realCRC, CRC); // testing CRC (end of message)
    }
}
