package seng302.PacketParsing;

import seng302.PacketParsing.BinaryMessageParserFactory;
import seng302.Race;

/**
 * Created by osr13 on 7/08/17.
 */
public class RaceRegistrationMessageParser extends BinaryMessageParserFactory {
    public RaceRegistrationMessageParser(byte[] packet) {
        super(packet);
    }

    @Override
    public void updateRace(Race race) {
        System.out.println("recieved RRM!!!! (yeah kevin!)");
    }
}
