package seng302.Server;

import seng302.Boat;
import seng302.packetGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.DataGenerator;
import seng302.Race;
import seng302.packetGeneration.RaceStatusGeneration.RaceStatusMessage;
import static java.lang.System.currentTimeMillis;

import java.util.*;

/**
 * Created by sba136 on 3/05/17.
 */
public class GeneratedData implements IServerData {
    private Queue<byte[]> bytes = new LinkedList<>();
    private BoatLocationMessage boatLocationMessage = new BoatLocationMessage();
    private Race race = new Race();

    // Generate RaceStatusMessage from using properties of Race object.
    private RaceStatusMessage rsm = new RaceStatusMessage(currentTimeMillis(),
                                                            race.getRaceID(),
                                                            race.getRaceStatus()    ,
                                                            currentTimeMillis(),
                                                            race.getWindDirection(),
                                                            race.getWindSpeed(),
                                                            (char)(race.getBoats().size() + 48),
                                                            race.getRaceType(),
                                                            race.getBoats());

    @Override
    public byte[] getData() {
        try {
            return bytes.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public boolean ready() {
        return !bytes.isEmpty();
    }

    class XMLSender extends TimerTask {
        @Override
        public void run() {
            DataGenerator dataGenerator = new DataGenerator();
            byte[] xmlBytes = boatLocationMessage.xmlMessage(dataGenerator.loadFile("Race.xml"), (short) 0, (short) 0);
//            System.out.println(Arrays.toString(xmlBytes) + "\nsize: " + (bytes.size()+1));
            bytes.add(xmlBytes);
        }
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (Boat boat : race.getBoats()) {
                bytes.add(boatLocationMessage.boatPositionMessage(boat));
                System.out.println("packet created");
            }
        }
    }

    class RaceRunner extends TimerTask {
        @Override
        public void run() {
            race.updateBoats();
        }
    }

    class RSMSender extends TimerTask {
        @Override
        public void run() {
            bytes.add(rsm.getRaceStatusMessage());
            System.out.println("Race Status BoatLocationMessage created");
        }
    }

    public void runServerTimers() {
        Timer timer = new Timer();
        timer.schedule(new RSMSender(), 0, 2000);
        timer.schedule(new XMLSender(), 0, 2000);
        timer.schedule(new BoatPosSender(), 0, 500);
        timer.schedule(new RaceRunner(), 0, 17);
    }
}
