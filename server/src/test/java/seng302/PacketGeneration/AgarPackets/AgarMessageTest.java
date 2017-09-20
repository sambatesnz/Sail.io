package seng302.PacketGeneration.AgarPackets;

import org.junit.Test;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ConfirmationStatus;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ParticipantConfirmationMessage;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ParticipantConfirmationMessageUtility;

import static org.junit.Assert.*;

/**
 * test agar messages
 */
public class AgarMessageTest {


    @Test
    public void boatSourceId() throws Exception {
        int boatSourceId = 200;
        BinaryMessage agarMessage = new AgarMessage(boatSourceId, 100, 100);
        byte[] actualMessage = new byte[AgarMessageUtility.BOAT_SOURCE_ID.getSize()];
        int expectedBoatSourceId = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarMessageUtility.BOAT_SOURCE_ID.getIndex(), actualMessage, AgarMessageUtility.BOAT_SOURCE_ID.getSize());
        assertEquals(boatSourceId, expectedBoatSourceId);
    }

    @Test
    public void numLives() throws Exception {
        int numLives = 3;
        BinaryMessage agarMessage = new AgarMessage(100, numLives, 100);
        byte[] actualMessage = new byte[AgarMessageUtility.LIVES.getSize()];
        int expectedNumLives = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarMessageUtility.LIVES.getIndex(), actualMessage, AgarMessageUtility.LIVES.getSize());
        assertEquals(numLives, expectedNumLives);
    }

    @Test
    public void boatSize() throws Exception {
        int boatSize = 10;
        BinaryMessage agarMessage = new AgarMessage(100, 3, boatSize);
        byte[] actualMessage = new byte[AgarMessageUtility.BOAT_SIZE.getSize()];
        int expectedBoatSize = PacketUtils.getIntFromByteArray(agarMessage.getBody(), AgarMessageUtility.BOAT_SIZE.getIndex(), actualMessage, AgarMessageUtility.BOAT_SIZE.getSize());
        assertEquals(boatSize, expectedBoatSize);
    }
}