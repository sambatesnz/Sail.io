package seng302.PacketParsing;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.PacketGenerationUtils;
import seng302.PacketGeneration.ServerMessageGeneration.ServerMessageGenerationUtils;

/**
 * Created by osr13 on 7/08/17.
 */
public abstract class BinaryMessageParserFactory {

    private byte[] packet;
    private int clientID;

    public BinaryMessageParserFactory(byte[] packet) {
        this.packet = ServerMessageGenerationUtils.unwrapBody(packet);
        this.clientID = ServerMessageGenerationUtils.unwrapHeader(packet);
    }

    private int getMessageLength() {
        return PacketParserUtils.getMessageBodyLength(packet);
    }

    protected byte[] getMessageBody() {
        int messageLen = getMessageLength();
        byte[] body = new byte[messageLen];
        System.arraycopy(packet,15, body,0, messageLen);
        return body;
    }

    public abstract void updateRace(IServerData race);

    int getClientID() {
        return clientID;
    }
}
