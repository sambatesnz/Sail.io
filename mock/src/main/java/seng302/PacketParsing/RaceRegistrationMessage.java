package seng302.PacketParsing;

import seng302.Client.Messages.RaceRegistrationType;
import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.ParticipantConfirmationGeneration.ParticipantConfirmationMessage;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.RaceObjects.Boat;

/**
 * Created by osr13 on 7/08/17.
 */
public class RaceRegistrationMessage extends ServerSideMessageFactory {

    private RaceRegistrationType raceRegistrationType;
    private byte[] body;

    public RaceRegistrationMessage(byte[] packet) {
        super(packet);
        this.body = this.getMessageBody();
        raceRegistrationType = parseRegistrationType();
    }

    public RaceRegistrationType getRegistrationType(){
        return raceRegistrationType;
    }

    private RaceRegistrationType parseRegistrationType() {
        byte[] message = new byte[8];
        int type = PacketUtils.getIntFromByteArray(this.body, 0, message, 4);
        return RaceRegistrationType.getType(type);
    }

    @Override
    public void updateRace(IServerData raceData) {
        if (raceRegistrationType ==  RaceRegistrationType.PARTICIPATE){
            Boat boat =  raceData.getRace().addBoat();
            System.out.println(boat.getSourceId());

            BinaryMessage confirmationMessage = new ParticipantConfirmationMessage(boat.getSourceId());
            byte[] message = confirmationMessage.createMessage();
            byte[] wrappedMessage = ServerMessageGenerationUtils.wrap(message, super.getClientID());

            raceData.addSingleMessage(wrappedMessage);
        }
        System.out.println("received RRM!");


    }
}
