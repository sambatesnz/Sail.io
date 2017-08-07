package seng302.PacketParsing;

import seng302.Race;

/**
 * Created by osr13 on 7/08/17.
 */
public abstract class BinaryMessageParserFactory {

    private byte[] packet;

    public BinaryMessageParserFactory(byte[] packet) {this.packet = packet;}

    public abstract void updateRace(Race race);
}
