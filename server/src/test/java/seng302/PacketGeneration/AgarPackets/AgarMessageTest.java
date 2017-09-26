package seng302.PacketGeneration.AgarPackets;

import org.junit.Test;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;

import static org.junit.Assert.*;

/**
 * test agar messages
 */
public class AgarMessageTest {


    @Test
    public void boatSourceId() throws Exception {
        int boatSourceId = 200;
        BinaryMessage agarMessage = new ServerAgarMessage(boatSourceId, 100, 100);
        byte[] actualMessage = new byte[AgarServerMessageUtility.BOAT_SOURCE_ID.getSize()];
        int expectedBoatSourceId = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarServerMessageUtility.BOAT_SOURCE_ID.getIndex(), actualMessage, AgarServerMessageUtility.BOAT_SOURCE_ID.getSize());
        assertEquals(boatSourceId, expectedBoatSourceId);
    }

    @Test
    public void numLives() throws Exception {
        int numLives = 3;
        BinaryMessage agarMessage = new ServerAgarMessage(100, numLives, 100);
        byte[] actualMessage = new byte[AgarServerMessageUtility.LIVES.getSize()];
        int expectedNumLives = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarServerMessageUtility.LIVES.getIndex(), actualMessage, AgarServerMessageUtility.LIVES.getSize());
        assertEquals(numLives, expectedNumLives);
    }

    @Test
    public void boatSize() throws Exception {
        int boatSize = 10;
        BinaryMessage agarMessage = new ServerAgarMessage(100, 3, boatSize);
        byte[] actualMessage = new byte[AgarServerMessageUtility.BOAT_SIZE.getSize()];
        int expectedBoatSize = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarServerMessageUtility.BOAT_SIZE.getIndex(), actualMessage, AgarServerMessageUtility.BOAT_SIZE.getSize());
        assertEquals(boatSize, expectedBoatSize);
    }
}