package seng302.PacketGeneration.ParticipantConfirmationGeneration;

import org.junit.Test;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;

import static org.junit.Assert.*;

/**
 * Used to test participant confirmation message
 */
public class ParticipantConfirmationMessageTest {

    @Test
    public void raceRegistration() throws Exception {
        int boatSourceId = 200;
        BinaryMessage participantConfirmationMessage = new ParticipantConfirmationMessage(boatSourceId);
        byte[] actualMessage = new byte[4];
        int expectedBoatSourceId = PacketUtils.getIntFromByteArray(participantConfirmationMessage.getBody(), 0, actualMessage, 4);
        assertEquals(boatSourceId, expectedBoatSourceId);
    }

}