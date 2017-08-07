package seng302;

import seng302.PacketGeneration.MessageType;
import seng302.PacketParsing.BinaryMessageParserFactory;
import seng302.PacketParsing.BoatActionMessageParser;
import seng302.PacketParsing.PacketParserUtils;
import seng302.PacketParsing.RaceRegistrationMessageParser;

/**
 * Created by osr13 on 7/08/17.
 */
public class RaceHandler {

    private final Race race;
    private BinaryMessageParserFactory binaryMessageParserFactory;

    public RaceHandler(Race race) {
        this.race = race;
    }


    public void updateRace(byte[] packet) {
        MessageType type = PacketParserUtils.getMessageType(packet);
        BinaryMessageParserFactory myMessage = decideMessage(packet);
        myMessage.updateRace(this.race);
    }


    private BinaryMessageParserFactory decideMessage(byte[] packet) {
        MessageType type = PacketParserUtils.getMessageType(packet);
        BinaryMessageParserFactory parser = null;
        switch (type){
            case BOAT_ACTION:
                parser = new BoatActionMessageParser(packet);
                break;
            case RACE_REGISTRATION:
                parser = new RaceRegistrationMessageParser(packet);
                break;
        }
        return parser;
    }

}
