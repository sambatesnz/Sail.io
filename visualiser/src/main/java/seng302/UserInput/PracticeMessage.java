package seng302.UserInput;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;

public class PracticeMessage  extends BinaryMessage {

    private static final int MESSAGE_SIZE = 1;
    private byte[] practiceRaceMessage;

    public static final byte START = 0;
    public static final byte END = 1;


    /**
     * Constructor for a practice race message
     */
    public PracticeMessage(byte meaning) {
        this.practiceRaceMessage = new byte[] {meaning};
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
