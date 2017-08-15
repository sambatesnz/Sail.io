package seng302.UserInput;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;
import seng302.UserInputController.BoatAction;
import seng302.UserInputController.BoatActionMessageUtility;

/**
 * Creates messages of boat action type as specified by SENG302 API
 * Implementation of Binary message
 */
public class BoatActionMessage extends BinaryMessage {

    private byte[] action;
    private static final int MESSAGE_SIZE = 5;
    private byte[] boatSourceId;
    private byte[] boatActionMessage;

    /**
     * Constructor for a boat action message
     * @param action the action you wish to perform (eg sail-in / sail-out)
     */
    BoatActionMessage(BoatAction action, int boatSourceid){
        this.action = PacketGenerationUtils.intToOneByte(action.getBoatAction());
        this.boatSourceId = PacketGenerationUtils.intToFourBytes(boatSourceid);
        this.boatActionMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(action, 0, boatActionMessage, BoatActionMessageUtility.BOAT_ACTION.getIndex(), BoatActionMessageUtility.BOAT_ACTION.getSize());
        System.arraycopy(boatSourceId, 0, boatActionMessage, BoatActionMessageUtility.BOAT_SOURCE_ID.getIndex(), BoatActionMessageUtility.BOAT_SOURCE_ID.getSize());
        return boatActionMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.BOAT_ACTION.getMessageType();
    }
}
