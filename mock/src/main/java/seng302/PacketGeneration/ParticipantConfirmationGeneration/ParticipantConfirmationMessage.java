package seng302.PacketGeneration.ParticipantConfirmationGeneration;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Confirms the boat sourceID to a client
 */
public class ParticipantConfirmationMessage extends BinaryMessage {

    private byte[] participantConfirmationMessage;
    private byte[] boatSourceId;
    private static final int MESSAGE_SIZE = 4;


    /**
     * Creates a participation confirmation message
     * @param boatSourceId source id of boat
     */
    public ParticipantConfirmationMessage(int boatSourceId) {
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceId);
        this.participantConfirmationMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(boatSourceId, 0, participantConfirmationMessage, 0, MESSAGE_SIZE);
        return participantConfirmationMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.PARTICIPANT_CONFIRMATION.getMessageType();
    }
}
