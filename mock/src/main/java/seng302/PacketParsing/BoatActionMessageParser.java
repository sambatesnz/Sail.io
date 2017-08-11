package seng302.PacketParsing;

import seng302.DataGeneration.IServerData;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

/**
 * Created by osr13 on 7/08/17.
 */
public class BoatActionMessageParser extends BinaryMessageParserFactory{

    private byte[] body;
    private BoatAction boatAction;

    public BoatActionMessageParser(byte[] packet) {
        super(packet);
        this.body = this.getMessageBody();
        this.boatAction = parseBoatAction();
    }

    private BoatAction parseBoatAction() {
        int action = PacketParserUtils.byteArrayToInt(body, 0, 1);
        System.out.println(BoatAction.getAction(action));
        return BoatAction.getAction(action);
    }

    @Override
    public void updateRace(IServerData race) {
        Delegator delegator = new Delegator(race.getRace());
        delegator.processCommand(boatAction);
        System.out.println("recieved boat action message");
    }

}
