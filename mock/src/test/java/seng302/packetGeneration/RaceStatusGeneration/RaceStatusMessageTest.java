package seng302.packetGeneration.RaceStatusGeneration;

import org.junit.Test;
import seng302.packetGeneration.PacketUtils;


import static org.junit.Assert.*;

/**
 * Test class for testing the race status messages
 * The tests test each section of the Race Status BoatLocationMessageDeprecated Specification as defined in page 9 of  https://docs.google.com/a/acracemgt.com/viewer?a=v&pid=sites&srcid=YWNyYWNlbWd0LmNvbXxub3RpY2Vib2FyZHxneDoyYTExNTQ4Yzg3ZGRmNTQ1
 */
public class RaceStatusMessageTest {


    @Test
    public void testMessagePacketSize() throws Exception {

        int headerSize = 24;
        int boatPacketSize = 20;
        char numBoats = '0';
        int numBoatsInt = 0;
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

        int expected =  headerSize + numBoatsInt * boatPacketSize;
        int packetSize = raceStatusMessage.getBody().length;
        assertEquals(packetSize, expected);
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
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION_POS;
        int size = RaceStatusUtility.MESSAGE_VERSION_SIZE;
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
        int sourceIndex = RaceStatusUtility.MESSAGE_VERSION_POS;
        int size = RaceStatusUtility.MESSAGE_VERSION_SIZE;
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
        int sourceIndex = RaceStatusUtility.CURRENT_TIME_POS;
        int size = RaceStatusUtility.CURRENT_TIME_SIZE;
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
        int sourceIndex = RaceStatusUtility.RACE_ID_POS;
        int size = RaceStatusUtility.RACE_ID_SIZE;
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
        int sourceIndex = RaceStatusUtility.RACE_STATUS_POS;
        int size = RaceStatusUtility.RACE_STATUS_SIZE;
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
        int sourceIndex = RaceStatusUtility.EXPECTED_START_TIME_POS;
        int size = RaceStatusUtility.EXPECTED_START_TIME_SIZE;
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
        int sourceIndex = RaceStatusUtility.WIND_DIRECTION_POS;
        int size = RaceStatusUtility.WIND_DIRECTION_SIZE;
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
        int sourceIndex = RaceStatusUtility.WIND_SPEED_POS;
        int size = RaceStatusUtility.WIND_SPEED_SIZE;
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
        int sourceIndex = RaceStatusUtility.NUM_BOATS_POS;
        int size = RaceStatusUtility.NUM_BOATS_SIZE;
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
        int sourceIndex = RaceStatusUtility.RACE_TYPE_POS;
        int size = RaceStatusUtility.RACE_TYPE_SIZE;
        byte[] actualMessage = new byte[2];
        char actualRaceType = PacketUtils.getCharFromByteArray(message, sourceIndex, actualMessage, size);
        assertEquals(raceType, actualRaceType);
    }

}