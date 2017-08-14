package seng302.PacketGeneration.ParticipantConfirmationGeneration;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Confirms the boat sourceID to a client, aka in the seng302 api as 'Registration Response Message'
 */
public class ParticipantConfirmationMessage extends BinaryMessage {

    private byte[] participantConfirmationMessage;
    private byte[] boatSourceId;
    private byte[] status;
    private static final int MESSAGE_SIZE = 5;


    /**
     * Creates a participation confirmation message
     * @param boatSourceId source id of boat
     * @param status
     */
    public ParticipantConfirmationMessage(int boatSourceId, ConfirmationStatus status) {
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceId);
        this.status = PacketGenerationUtils.intToOneByte(status.getStatus());
        this.participantConfirmationMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(boatSourceId, 0, participantConfirmationMessage, ParticipantConfirmationMessageUtility.SOURCE_ID.getIndex(), ParticipantConfirmationMessageUtility.SOURCE_ID.getSize());
        System.arraycopy(status, 0, participantConfirmationMessage, ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getIndex(), ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getSize());
        return participantConfirmationMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.PARTICIPANT_CONFIRMATION.getMessageType();
    }
}
