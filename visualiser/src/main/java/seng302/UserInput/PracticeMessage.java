package seng302.UserInput;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Generates a binary practice message
 */
public class PracticeMessage  extends BinaryMessage {

    private static final int MESSAGE_SIZE = 5;
    private byte[] practiceRaceMessage;
    private byte[] boatSourceId;
    private byte meaning;


    /**
     * Constructor for a practice race message
     * @param meaning
     */
    public PracticeMessage(PracticeMessageMeaning meaning) {
        practiceRaceMessage = new byte[MESSAGE_SIZE];
        this.meaning = (byte) meaning.getValue();
        boatSourceId = PacketGenerationUtils.intToFourBytes(0);
    }

    /**
     * Constructor for a practice race message with sourceId
     */
    public PracticeMessage(PracticeMessageMeaning meaning, int boatSourceid) {
        this.practiceRaceMessage = new byte[MESSAGE_SIZE];
        this.meaning = (byte) meaning.getValue();
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceid);
    }


    @Override
    public byte[] getBody() {
        practiceRaceMessage[0] = meaning;
        System.arraycopy(boatSourceId, 0, practiceRaceMessage, 1, 4);
        return practiceRaceMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.PRACTICE.getMessageType();
    }
}
