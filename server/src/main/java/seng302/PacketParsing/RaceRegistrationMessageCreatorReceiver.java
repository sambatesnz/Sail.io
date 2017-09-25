package seng302.PacketParsing;

import seng302.Client.Messages.RaceRegistrationMessage;
import seng302.Client.Messages.RaceRegistrationType;
import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ConfirmationStatus;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ParticipantConfirmationMessage;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.RaceObjects.Boat;

/**
 * Takes a wrapped race registration message and tries to add a boat if someone wants to participate
 */
public class RaceRegistrationMessageCreatorReceiver extends ServerSideMessageFactory {

    private RaceRegistrationType raceRegistrationType;
    private byte[] body;

    public RaceRegistrationMessageCreatorReceiver(byte[] packet) {
        super(packet);
        this.body = this.getMessageBody();
        raceRegistrationType = parseRegistrationType();
    }

    public RaceRegistrationType getRegistrationType(){
        return raceRegistrationType;
    }

    private RaceRegistrationType parseRegistrationType() {
        byte[] message = new byte[8];
        int type = PacketUtils.getIntFromByteArray(this.body, 0, message, RaceRegistrationMessage.getMessageSize());
        return RaceRegistrationType.getType(type);
    }

    @Override
    public void updateRace(IServerData raceData) {
        if (raceData.getRace().isPracticeRace()) return;
        if (raceRegistrationType ==  RaceRegistrationType.PARTICIPATE){
            BinaryMessage confirmationMessage;
            if (raceData.getRace().getRaceStatus() == RaceStatus.WARNING || raceData.getRace().getRaceStatus() == RaceStatus.START_TIME_NOT_SET) {
                Boat boat = null;
                try {
                    boat = raceData.getRace().addBoat(super.getClientID());
                    confirmationMessage = new ParticipantConfirmationMessage(boat.getSourceId(), ConfirmationStatus.PLAYING);
                } catch (Exception e) {
                    //TODO refactor this class so it doesnt have to send same PCM in different places
                    confirmationMessage = new ParticipantConfirmationMessage(0, ConfirmationStatus.SPECTATING);
                }
            }
            else {
                confirmationMessage = new ParticipantConfirmationMessage(0, ConfirmationStatus.SPECTATING);
            }
            byte[] message = confirmationMessage.createMessage();
            byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(message, super.getClientID());

            raceData.addSingleMessage(wrappedMessage);
        }
    }
}
