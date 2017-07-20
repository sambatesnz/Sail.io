package seng302.UserInputController;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Creates messages of boat action type as specified by SENG302 API
 * Implementation of Binary message
 */
public class BoatActionMessage extends BinaryMessage {

    private byte[] action;
    private static final int MESSAGE_SIZE = 1;
    private byte[] boatActionMessage;

    /**
     * Constructor for a boat action message
     * @param action the action you wish to perform (eg sail-in / sail-out)
     */
    BoatActionMessage(int action){
        this.action = PacketGenerationUtils.intToOneByte(action);
        this.boatActionMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(action, 0, boatActionMessage, 0, MESSAGE_SIZE);
        return boatActionMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.BOAT_ACTION.getMessageType();
    }
}
