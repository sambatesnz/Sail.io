package seng302.Client.Messages;

import seng302.PacketGeneration.ParticipantConfirmationGeneration.ConfirmationStatus;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ParticipantConfirmationMessageUtility;
import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.Race;

/**
 * Class to parse client participation messages and update the state of the race
 */
public class ClientParticipantConfirmationMessage extends ClientSideMessageParser{

    private int sourceId;
    private byte[] message;
    private ConfirmationStatus status;

    public ClientParticipantConfirmationMessage(byte[] body) {
        super(body);
        this.message = body;
        this.status = getStatus();
        this.sourceId = parseBody();
        System.out.println("Client SOURCE ID: " + this.sourceId);
    }

    private ConfirmationStatus getStatus() {
        return ConfirmationStatus.getEnum(PacketParserUtils.byteArrayToInt(message,
            ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getIndex(),
            ParticipantConfirmationMessageUtility.CONFIRMATION_STATUS.getSize()));
    }

    private int parseBody() {
        return PacketParserUtils.byteArrayToInt(message,
                ParticipantConfirmationMessageUtility.SOURCE_ID.getIndex(),
                ParticipantConfirmationMessageUtility.SOURCE_ID.getSize());
    }

    @Override
    public void updateRace(Race race) {
        race.setClientSourceId(sourceId);
    }
}
