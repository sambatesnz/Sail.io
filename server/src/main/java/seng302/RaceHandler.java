package seng302;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.MessageType;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;
import seng302.PacketParsing.*;
import seng302.Server.ConnectionListener;
import seng302.UserInput.PracticeMessageMeaning;

/**
 * Updates the race based on the message type (eg Boat action or race registration)
 */
public class RaceHandler {

    private IServerData race;
    private ConnectionListener connectionListener;

    public RaceHandler(IServerData race, ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
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
                if (((PracticeRaceMessageReceiver) parser).getMeaning() == PracticeMessageMeaning.START) {
                    //If someone wants to practise, we have to limit all other incoming connections
                    connectionListener.setListening(false);
                } else {
                    connectionListener.setListening(true);
                }
                break;
        }
        return parser;
    }
}
