package seng302;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.PacketParsing.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Updates the race based on the message type (eg Boat action or race registration)
 */
public class RaceHandler {

    private IServerData race;

    public RaceHandler(IServerData race) {
        this.race = race;
    }


    public void updateRace(byte[] packet) {
        ServerSideMessageFactory myMessage = decideMessage(packet);
        myMessage.updateRace(this.race);
    }

    private ServerSideMessageFactory decideMessage(byte[] packet) {
        MessageType type = PacketParserUtils.getMessageType(ServerMessageGenerationUtils.unwrapBody(packet));
        ServerSideMessageFactory parser = null;
        switch (type){
            case BOAT_ACTION:
                parser = new BoatActionMessageReceiver(packet);
                break;
            case RACE_REGISTRATION:
                parser = new RaceRegistrationMessageCreatorReceiver(packet);
                break;
            case PRACTICE:
                parser = new PracticeRaceMessageReceiver(packet);
                break;
        }
        return parser;
    }
}
