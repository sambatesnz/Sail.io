package seng302;

import org.junit.Test;

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
        long transformed = (new Message()).get4BytePos(latitude);
        assertEquals(1<<29, transformed);
    }

    @Test
    public void testGet4BytePosN45() throws Exception {
        double latitude = -45.0;
        long transformed = (new Message()).get4BytePos(latitude);
        assertEquals(-(1<<29), transformed);
    }
    @Test
    public void testGet2ByteHeading90() throws Exception {
        double heading = 90;
        long transformed = (new Message()).get2ByteHeading(heading);
        assertEquals(1<<14, transformed);
    }
}
