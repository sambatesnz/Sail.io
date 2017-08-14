package seng302.Client.Messages;

import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.PacketGenerationUtils;

/**
 * Upon connection to the server, this packet is sent to retrieve a boat SourceID.
 */
public class RaceRegistrationMessage extends BinaryMessage {

    private byte[] raceRegistrationMessage;
    private byte[] registrationType;
    private static final int MESSAGE_SIZE = 1;


    /**
     * Constructor
     * @param registrationType
     */
    public RaceRegistrationMessage(RaceRegistrationType registrationType) {
        this.registrationType = PacketGenerationUtils.intToOneByte(registrationType.getRegistrationType());
        this.raceRegistrationMessage = new byte[MESSAGE_SIZE];
    }

    @Override
    public byte[] getBody() {
        System.arraycopy(registrationType, 0, raceRegistrationMessage, 0, MESSAGE_SIZE);
        return raceRegistrationMessage;
    }

    @Override
    protected int getMessageType() {
        return MessageType.RACE_REGISTRATION.getMessageType();
    }

    public static int getMessageSize() {
        return MESSAGE_SIZE;
    }
}
