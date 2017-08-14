package seng302;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.PacketParsing.ServerSideMessageFactory;
import seng302.PacketParsing.BoatActionMessageCreator;
import seng302.PacketParsing.PacketParserUtils;
import seng302.PacketParsing.RaceRegistrationMessageCreator;

/**
 * Updates the race based on the message type (eg Boat action or race registration)
 */
public class RaceHandler {

    private final IServerData race;

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
                parser = new BoatActionMessageCreator(packet);
                break;
            case RACE_REGISTRATION:
                parser = new RaceRegistrationMessageCreator(packet);
                break;
        }
        return parser;
    }

}
