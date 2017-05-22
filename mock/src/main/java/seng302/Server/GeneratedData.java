package seng302.Server;

import seng302.Boat;
import seng302.packetGeneration.BinaryMessage;
import seng302.packetGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.packetGeneration.BoatLocationGeneration.BoatLocationMessageDeprecated;
import seng302.DataGenerator;
import seng302.Race;
import seng302.packetGeneration.BoatLocationGeneration.BoatLocationUtility;
import seng302.packetGeneration.RaceStatusGeneration.RaceStatusMessage;
import seng302.packetGeneration.XMLMessageGeneration.XMLMessage;

import static java.lang.System.currentTimeMillis;

import java.util.*;

/**
 * Created by sba136 on 3/05/17.
 */
public class GeneratedData implements IServerData {
    private Queue<byte[]> bytes = new LinkedList<>();

    private Race race = new Race();
    // Generate RaceStatusMessage from using properties of Race object.
    private BinaryMessage rsm = new RaceStatusMessage(currentTimeMillis(),
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
            BinaryMessage xmlMessage =  new XMLMessage(dataGenerator.loadFile("Race.xml"), (short)0, (short) 0);
            System.out.println("\n--------\nXML Message created");
            System.out.println(Arrays.toString(xmlMessage.createMessage()));
            System.out.println("--------\n");
            bytes.add(xmlMessage.createMessage());
        }
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (Boat boat : race.getBoats()) {
                BinaryMessage boatLocationMessage = new BoatLocationMessage(
                        1, System.currentTimeMillis(), 1,
                        1, 1,
                        boat.getLatitude(), boat.getLongitude(), 0,
                        (short) boat.getHeading(), 0, 0, (short) boat.getSpeed(),
                        (short) 100, (short) 100,
                        (short) 200, (short) 200,
                        (short) 100, (short) 100, (short) 100,
                        (short) 100, (short) 100, (short) 100
                );
                System.out.println("\n--------\nBoat location message packet created");
                System.out.println(Arrays.toString(boatLocationMessage.createMessage()));
                System.out.println("--------\n");
                bytes.add(boatLocationMessage.createMessage());

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
            bytes.add(rsm.createMessage());
            System.out.println("\n--------\nRace Status message packet created");
            System.out.println(Arrays.toString(rsm.createMessage()));
            System.out.println("--------\n");
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
