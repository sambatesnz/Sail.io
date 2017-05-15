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
        char numBoats = '0';
        int numBoatsInt = 0;

        RaceStatusMessage raceStatusMessage =  new RaceStatusMessage(
                1,
                System.currentTimeMillis(),
                1,
                2,
                System.currentTimeMillis(),
                WindDirection.NORTH,
                15,
                numBoats,
                '1',
                null
        );


        int expected =  headerSize + numBoatsInt * boatPacketSize;
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
                '0',
                '0',
                null
        );


        byte[] message = raceStatusMessage.getRaceStatusMessage();
        byte[] actualMessage = new byte[8];
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION;
        int size = RaceStatusUtility.MESSAGE_VERSION_SIZE;
        long expectedTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertTrue(false); //Not implemented yet
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
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.CURRENT_TIME;
        int size = RaceStatusUtility.CURRENT_TIME_SIZE;
        byte[] actualMessage = new byte[8];
        long expectedTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, expectedTime);
    }

    @Test
    public void raceId() throws Exception {
        int raceId = 1;

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                0,
                raceId,
                0,
                0,
                0,
                0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.RACE_ID;
        int size = RaceStatusUtility.RACE_ID_SIZE;
        byte[] actualMessage = new byte[4];
        int expectedRaceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceId, expectedRaceId);

    }

    @Test
    public void raceStatus() throws Exception {
        int raceStatus = RaceStatus.STARTED.value();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                0,
                0,
                raceStatus,
                0,
                0,
                0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.RACE_STATUS;
        int size = RaceStatusUtility.RACE_STATUS_SIZE;
        byte[] actualMessage = new byte[4];
        int expectedRaceStatus = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceStatus, expectedRaceStatus);
    }

    @Test
    public void expectedStartTime() throws Exception {
        long startTime = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                0,
                0,
                0,
                startTime,
                0,
                0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.EXPECTED_START_TIME;
        int size = RaceStatusUtility.EXPECTED_START_TIME_SIZE;
        byte[] actualMessage = new byte[8];
        long expectedTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(startTime, expectedTime);
    }

    @Test
    public void windDirection() throws Exception {
        assertTrue(false);//Not implemented yet
    }

    @Test
    public void windSpeed() throws Exception {
        assertTrue(false);//Not implemented yet
    }

    @Test
    public void numBoatsInRace() throws Exception {
        char numBoats = '1';

        long startTime = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                numBoats,
                '0',
                null
        );
        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.NUM_BOATS;
        int size = RaceStatusUtility.NUM_BOATS_SIZE;
        byte[] actualMessage = new byte[2];
        char actualNumBoats = PacketUtils.getCharFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(numBoats, actualNumBoats);
    }

    @Test
    public void raceType() throws Exception {

        char raceType = RaceType.MATCH_RACE.value();


        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                1,
                0,
                0,
                0,
                0,
                0,
                0,
                '0',
                raceType,
                null
        );
        byte[] message = raceStatusMessage.getRaceStatusMessage();
        int sourceIndex = RaceStatusUtility.RACE_TYPE;
        int size = RaceStatusUtility.RACE_TYPE_SIZE;
        byte[] actualMessage = new byte[2];
        char actualRaceType = PacketUtils.getCharFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceType, actualRaceType);
    }

}