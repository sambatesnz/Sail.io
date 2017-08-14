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
    public void sourceID() throws Exception {
        int boatSourceId = 200;
        BinaryMessage participantConfirmationMessage = new ParticipantConfirmationMessage(boatSourceId, ConfirmationStatus.PLAYING);
        byte[] actualMessage = new byte[ParticipantConfirmationMessageUtility.SOURCE_ID.getSize()];
        int expectedBoatSourceId = PacketUtils.getIntFromByteArray(participantConfirmationMessage.getBody(), ParticipantConfirmationMessageUtility.SOURCE_ID.getIndex(), actualMessage, ParticipantConfirmationMessageUtility.SOURCE_ID.getSize());
        assertEquals(boatSourceId, expectedBoatSourceId);
    }

    @Test
    public void status() throws Exception {
        ConfirmationStatus status = ConfirmationStatus.PLAYING;
        BinaryMessage participantConfirmationMessage = new ParticipantConfirmationMessage(200, status);
        byte[] actualMessage = new byte[4];
        int expectedStatus = PacketUtils.getIntFromByteArray(participantConfirmationMessage.getBody(), ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getIndex(), actualMessage, ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getSize());
        assertEquals(status.getStatus(), expectedStatus);
    }

}