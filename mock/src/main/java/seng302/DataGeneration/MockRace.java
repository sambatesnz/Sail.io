package seng302.DataGeneration;

import seng302.Boat;
import seng302.DataGenerator;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.PacketGeneration.RaceStatusGeneration.RaceStatusMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLSubTypes;
import seng302.Race;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sba136 on 3/05/17.
 */
public class MockRace implements IServerData {
    private Queue<byte[]> bytes = new LinkedBlockingQueue<>();

    Timer timer = new Timer();

    private Race race = new Race();

    // Generate RaceStatusMessage from using properties of Race object.
    private BinaryMessage rsm;

    public Race getRace() {
        return race;
    }

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

    @Override
    public void beginGeneratingData() {
        timer.schedule(new XMLSender(), 0, 2000);
        timer.schedule(new RSMSender(), 100, 2000);
        timer.schedule(new BoatPosSender(), 1000, 100);
        timer.schedule(new RaceRunner(), 2000, 100);
    }

    @Override
    public void finishGeneratingData() {
        System.out.println("Threads cancelled");
        timer.cancel();
    }

    class XMLSender extends TimerTask {
        @Override
        public void run() {
            DataGenerator dataGenerator = new DataGenerator();

            BinaryMessage xmlMessage =  new XMLMessage(dataGenerator.loadFile("Race.xml"), (short)0, XMLSubTypes.RACE.getSubType(),  (short) 0);
            System.out.println("\n--------\nRace XML Message created");
            System.out.println(Arrays.toString(xmlMessage.createMessage()));
            System.out.println("--------\n");
            bytes.add(xmlMessage.createMessage());

            BinaryMessage boatsXml = new XMLMessage(dataGenerator.loadFile("Boats.xml"), (short)0, XMLSubTypes.BOAT.getSubType(), (short) 0);
            System.out.println("\n--------\nBoats XML Message created");
            System.out.println(Arrays.toString(boatsXml.createMessage()));
            System.out.println("--------\n");
            bytes.add(boatsXml.createMessage());

            BinaryMessage regattaXML = new XMLMessage(dataGenerator.loadFile("Regatta.xml"), (short)0, XMLSubTypes.REGATTA.getSubType(), (short) 0);
            System.out.println("\n--------\nRegatta XML Message created");
            System.out.println(Arrays.toString(regattaXML.createMessage()));
            System.out.println("--------\n");
            bytes.add(regattaXML.createMessage());
        }
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (Boat boat : race.getBoats()) {
                BinaryMessage boatLocationMessage = new BoatLocationMessage(
                        1, System.currentTimeMillis(), boat.getSourceId(),
                        1, 1,
                        boat.getLatitude(), boat.getLongitude(), 0,
                        (short) boat.getHeading(), 0, 0, (short) boat.getSpeed(),
                        (short) 100, (short) 100,
                        (short) 200, (short) 200,
                        (short) 100, (short) 100, (short) 100,
                        (short) 100, (short) 100, (short) 100
                );
//                if (boat.getSourceId() == 103) {
//                    System.out.println("\n--------\nBoat location message packet created");
//                    System.out.println(Arrays.toString(boatLocationMessage.createMessage()));
//                    System.out.println("--------\n");
//                }
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
            rsm = new RaceStatusMessage(currentTimeMillis(),
                    race.getRaceID(),
                    race.getRaceStatus(),
                    currentTimeMillis(),
                    race.getWindDirection(),
                    race.retrieveWindSpeed(),   // retrieve a new randomly generated wind speed
                    (char)(race.getBoats().size() + 48),
                    race.getRaceType(),
                    race.getBoats());
            bytes.add(rsm.createMessage());
            System.out.println("\n--------\nRace Status message packet created");
            System.out.println(Arrays.toString(rsm.createMessage()));
            System.out.println("--------\n");
        }
    }

}
