package seng302.UserInputController;


import seng302.MessageType;
import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.PacketGenerationUtils;

/**
 * Created by tjg73 on 13/07/17.
 */
public class BoatActionMessage extends BinaryMessage {

    private byte[] action;
    private static int MESSAGE_SIZE = 1;
    private byte[] boatActionMessage;

    public BoatActionMessage(int action){
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
