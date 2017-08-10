package seng302.PacketParsing;

import seng302.Client.Messages.RaceRegistrationType;
import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.PacketUtils;
import seng302.Race;
import seng302.RaceObjects.Boat;

/**
 * Created by osr13 on 7/08/17.
 */
public class RaceRegistrationMessageParser extends BinaryMessageParserFactory {

    private RaceRegistrationType raceRegistrationType;
    private byte[] body;

    public RaceRegistrationMessageParser(byte[] packet) {
        super(packet);
        this.body = this.getRaceBody();
        raceRegistrationType = parseRegistrationType();
    }

    public RaceRegistrationType getRegistrationType(){
        return raceRegistrationType;
    }

    private RaceRegistrationType parseRegistrationType() {
        byte[] message = new byte[8];
        int type = PacketUtils.getIntFromByteArray(this.body, 0, message, 4);
        return RaceRegistrationType.getType(type);
    }

    @Override
    public void updateRace(IServerData raceData) {
        Boat boat =  raceData.getRace().addBoat();
        System.out.println(boat.getSourceId());
        System.out.println("received RRM!");


    }
}
