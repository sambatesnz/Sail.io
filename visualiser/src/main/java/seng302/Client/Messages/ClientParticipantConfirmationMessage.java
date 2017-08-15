package seng302.Client.Messages;

import seng302.PacketParsing.PacketParserUtils;
import seng302.Race.Race;

import java.util.Arrays;

/**
 * Created by sba136 on 11/08/17.
 */
public class ClientParticipantConfirmationMessage extends ClientSideMessageParser{

    private int sourceId;
    private byte[] message;
    private static int SOURCE_ID_SIZE = 4;

    public ClientParticipantConfirmationMessage(byte[] body) {
        super(body);
        this.message = body;
        this.sourceId = parseBody();
        System.out.println(Arrays.toString(body));
        System.out.println("Client has SOURCE ID of " + this.sourceId);
    }

    private int parseBody() {
        return PacketParserUtils.byteArrayToInt(message, 0, SOURCE_ID_SIZE);
    }

    @Override
    public void updateRace(Race race) {
        race.setClientSourceId(sourceId);
    }
}
