package seng302.UserInput;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;

public class PracticeMessage  extends BinaryMessage {

    private static final int MESSAGE_SIZE = 0;
    private byte[] practiceRaceMessage;

    /**
     * Constructor for a practice race message
     */
    public PracticeMessage() {
        this.practiceRaceMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        return practiceRaceMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.PRACTICE.getMessageType();
    }
}
