package seng302.PacketGeneration.RaceStatusGeneration;

import org.junit.Test;
import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.RaceStatus;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the race status messages
 */
public class RaceStatusMessageTest {


    @Test
    public void testMessagePacketSize() throws Exception {

        int headerSize = 24;
        int boatPacketSize = 20;
        char numBoats = (char) 6;
        int numBoatsInt = 6;
        short windDirection = 0;

        RaceStatusMessage raceStatusMessage =  new RaceStatusMessage(
                System.currentTimeMillis(),
                1,
                2,
                System.currentTimeMillis(),
                windDirection,
                windDirection,
                numBoats,
                '1',
                null
        );

        int expected =  headerSize + (numBoatsInt) * boatPacketSize;
        int packetSize = raceStatusMessage.getBody().length;
        assertEquals(expected, packetSize);
    }

    @Test
    public void testMessageVNumber() throws Exception {
        int versionNumber = 5;
        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                versionNumber,
                0,
                0,
                0,
                0,
                (short)0,
                (short) 0,
                '0',
                '0',
                null
        );


        byte[] message = raceStatusMessage.getBody();
        byte[] actualMessage = new byte[8];
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION.getIndex();
        int size = RaceStatusUtility.MESSAGE_VERSION.getSize();
        int expectedVNumber = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(versionNumber, expectedVNumber);
    }

    @Test
    public void testDefaultMessageVersionNumber() throws Exception {
        int versionNumber = RaceStatusMessage.CURRENT_VERSION_NUMBER;
        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                0,
                (short)0,
                (short) 0,
                '0',
                '0',
                null
        );


        byte[] message = raceStatusMessage.getBody();
        byte[] actualMessage = new byte[8];
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION.getIndex();
        int size = RaceStatusUtility.MESSAGE_VERSION.getSize();
        int expectedVNumber = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(versionNumber, expectedVNumber);

    }

    @Test
    public void testCurrentTime() throws Exception {
        long time = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                time,
                0,
                0,
                0,
                (short)0,
                (short) 0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.CURRENT_TIME.getIndex();
        int size = RaceStatusUtility.CURRENT_TIME.getSize();
        byte[] actualMessage = new byte[8];
        long expectedTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(time, expectedTime);
    }

    @Test
    public void raceId() throws Exception {
        int raceId = 1;

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                raceId,
                0,
                0,
                (short)0,
                (short)0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.RACE_ID.getIndex();
        int size = RaceStatusUtility.RACE_ID.getSize();
        byte[] actualMessage = new byte[4];
        int expectedRaceId = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceId, expectedRaceId);

    }

    @Test
    public void raceStatus() throws Exception {
        int raceStatus = RaceStatus.STARTED.value();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                raceStatus,
                0,
                (short) 0,
                (short) 0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.RACE_STATUS.getIndex();
        int size = RaceStatusUtility.RACE_STATUS.getSize();
        byte[] actualMessage = new byte[4];
        int expectedRaceStatus = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceStatus, expectedRaceStatus);
    }

    @Test
    public void expectedStartTime() throws Exception {
        long startTime = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                startTime,
                (short) 0,
                (short) 0,
                '0',
                '0',
                null
        );

        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.EXPECTED_START_TIME.getIndex();
        int size = RaceStatusUtility.EXPECTED_START_TIME.getSize();
        byte[] actualMessage = new byte[8];
        long expectedTime = PacketUtils.getLongFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(startTime, expectedTime);
    }

    @Test
    public void windDirection() throws Exception {
        short windDirection = WindDirection.EAST;
        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                0,
                windDirection,
                (short) 0,
                '0',
                '0',
                null
        );
        byte[] message = raceStatusMessage.getBody();
        byte[] actualMessage = new byte[4];
        int sourceIndex = RaceStatusUtility.WIND_DIRECTION.getIndex();
        int size = RaceStatusUtility.WIND_DIRECTION.getSize();
        int expectedWindDirection = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(windDirection, expectedWindDirection);
    }

    @Test
    public void windSpeed() throws Exception {
        short windSpeed = 30;
        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                0,
                (short) 0,
                windSpeed,
                '0',
                '0',
                null
        );
        byte[] message = raceStatusMessage.getBody();
        byte[] actualMessage = new byte[4];
        int sourceIndex = RaceStatusUtility.WIND_SPEED.getIndex();
        int size = RaceStatusUtility.WIND_SPEED.getSize();
        int expectedWindSpeed = PacketUtils.getIntFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(windSpeed, expectedWindSpeed);
    }

    @Test
    public void numBoatsInRace() throws Exception {
        char numBoats = '1';

        long startTime = System.currentTimeMillis();

        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                0,
                (short) 0,
                (short)0,
                numBoats,
                '0',
                null
        );
        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.NUM_BOATS.getIndex();
        int size = RaceStatusUtility.NUM_BOATS.getSize();
        byte[] actualMessage = new byte[2];
        char actualNumBoats = PacketUtils.getCharFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(numBoats, actualNumBoats);
    }

    @Test
    public void raceType() throws Exception {

        char raceType = RaceType.MATCH_RACE.value();


        RaceStatusMessage raceStatusMessage = new RaceStatusMessage(
                0,
                0,
                0,
                0,
                (short) 0,
                (short) 0,
                '0',
                raceType,
                null
        );
        byte[] message = raceStatusMessage.getBody();
        int sourceIndex = RaceStatusUtility.RACE_TYPE.getIndex();
        int size = RaceStatusUtility.RACE_TYPE.getSize();
        byte[] actualMessage = new byte[2];
        char actualRaceType = PacketUtils.getCharFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceType, actualRaceType);
    }

}