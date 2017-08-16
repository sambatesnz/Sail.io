package seng302.PacketParsing;

import seng302.DataGeneration.IServerData;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

/**
 * Takes a wrapped Boat Action message and tries to update the state of the boat based on its id
 */
public class BoatActionMessageReceiver extends ServerSideMessageFactory {

    private byte[] body;
    private BoatAction boatAction;
    private int sourceId;

    public BoatActionMessageReceiver(byte[] packet) {
        super(packet);
        this.body = getMessageBody();
        this.boatAction = parseBoatAction();
        this.sourceId = parseSourceId();
    }

    private int parseSourceId() {
        int sourceId = PacketParserUtils.byteArrayToInt(body, 1, 4);
        System.out.println("BOAT SOURCE ID YO " + sourceId);
        return sourceId;
    }

    private BoatAction parseBoatAction() {
        int action = PacketParserUtils.byteArrayToInt(body, 0, 1);

        return BoatAction.getAction(action);
    }

    @Override
    public void updateRace(IServerData race) {
        Delegator delegator = new Delegator(race.getRace());
        delegator.processCommand(boatAction, sourceId);
        System.out.println("recieved boat action message");
    }

}
