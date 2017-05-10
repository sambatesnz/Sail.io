package seng302;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by tjg73 on 10/05/17.
 */
public class RaceStatusMessageTest {


    @Test
    public void test() throws Exception {

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
}