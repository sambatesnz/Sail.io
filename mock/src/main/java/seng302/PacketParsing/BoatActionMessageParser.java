package seng302.PacketParsing;

import seng302.Race;

/**
 * Created by osr13 on 7/08/17.
 */
public class BoatActionMessageParser extends BinaryMessageParserFactory{


    public BoatActionMessageParser(byte[] packet) {
        super(packet);
    }

    @Override
    public void updateRace(Race race) {
        System.out.println("recieved boat action message");
    }
}
