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
    public void testMessagePacketSize() throws Exception {
        int boatPacketSize = 20;

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                0,
                '0',
                '0',
                0L,
                0L);

        int actualSize = boatStatusMessage.getBoatStatusMessage().length;
        assertEquals(boatPacketSize, actualSize);
    }

        @Test
    public void testSourceId() throws Exception {
        int sourceBoatID = 7;

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                sourceBoatID,
                '0',
                '0',
                0L,
                0L);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[4];
        int actualBoatID = PacketUtils.getIntFromByteArray(message, RaceStatusUtility.SOURCE_ID_INDEX, actualMessage, 4);
        assertEquals(sourceBoatID, actualBoatID);
    }

    @Test
    public void testBoatStatus() throws Exception {
        char boatStatus = BoatStatus.FINISHED.value();

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                0,
                boatStatus,
                '0',
                0L,
                0L);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[2];
        char actualBoatStatus = PacketUtils.getCharFromByteArray(message, RaceStatusUtility.BOAT_STATUS_INDEX, actualMessage, 1);
        assertEquals(boatStatus, actualBoatStatus);
    }

    @Test
    public void testLegNumber() throws Exception {
        char legNumber = '8';

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                0,
                '0',
                legNumber,
                0L,
                0L);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[2];
        char actualLegNumber = PacketUtils.getCharFromByteArray(message, RaceStatusUtility.LEG_NUMBER_INDEX, actualMessage, 1);
        assertEquals(legNumber, actualLegNumber);
    }

    @Test
    public void testEstTimeToMark() throws Exception {
        long estTimeAtNextMark = System.currentTimeMillis() + 120000L;

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                0,
                '0',
                '0',
                estTimeAtNextMark,
                0L);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[8];
        long actualEstTimeAtNextMark = PacketUtils.getLongFromByteArray(message, RaceStatusUtility.EST_TIME_AT_NEXT_MARK_INDEX, actualMessage, 6);
        assertEquals(estTimeAtNextMark, actualEstTimeAtNextMark);
    }

    @Test
    public void testEstTimeToFinish() throws Exception {
        long estTimeAtFinish = System.currentTimeMillis() + 360000L;

        BoatStatusMessage boatStatusMessage = new BoatStatusMessage(
                0,
                '0',
                '0',
                0L,
                estTimeAtFinish);

        byte[] message = boatStatusMessage.getBoatStatusMessage();

        byte[] actualMessage = new byte[8];
        long actualEstTimeAtFinish = PacketUtils.getLongFromByteArray(message, RaceStatusUtility.EST_TIME_AT_FINISH_INDEX, actualMessage, 6);
        assertEquals(estTimeAtFinish, actualEstTimeAtFinish);
    }


}