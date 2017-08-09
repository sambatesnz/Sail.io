package seng302.PacketParsing;

import seng302.Race;

/**
 * Created by osr13 on 7/08/17.
 */
public abstract class BinaryMessageParserFactory {

    private byte[] packet;

    public BinaryMessageParserFactory(byte[] packet) {this.packet = packet;}

    private int getMessageLength() {
        return PacketParserUtils.getMessageBodyLength(packet);
    }

    protected byte[] getRaceBody() {
        int messageLen = getMessageLength();
        byte[] body = new byte[messageLen];
        System.arraycopy(packet,15, body,0, messageLen);
        return body;
    }

    public abstract void updateRace(Race race);
}
