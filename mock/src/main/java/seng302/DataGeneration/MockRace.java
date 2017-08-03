package seng302.DataGeneration;

import seng302.DataGenerator;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.PacketGeneration.RaceStatusGeneration.RaceStatusMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLSubTypes;
import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.XMLCreation.RaceXMLCreator;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sba136 on 3/05/17.
 */
public class MockRace implements IServerData {

    private Race race;
    private BinaryMessage rsm;
    private Queue<byte[]> bytes;
    Timer timer = new Timer();


    public MockRace(){
        this.race = new Race();
        bytes = new LinkedBlockingQueue<>();
    }


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
        timer.schedule(new RSMSender(), 100, 500);
        timer.schedule(new BoatPosSender(), 1000, 17);
        timer.schedule(new RaceRunner(), 2000, 17);
    }

    @Override
    public void finishGeneratingData() {
        System.out.println("Threads cancelled");
        timer.cancel();
    }

    @Override
    public void addXMLPackets() {
        generateXML();
    }

    class XMLSender extends TimerTask {
        @Override
        public void run() {
            generateXML();
        }
    }

    private void generateXML() {
        DataGenerator dataGenerator = new DataGenerator();

        RaceXMLCreator creator = new RaceXMLCreator(race);
        String xml = "";
        try {
            xml = creator.createDocument().asXML();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BinaryMessage raceXML =  new XMLMessage(xml, (short)0, XMLSubTypes.RACE.getSubType(),  (short) 0);
        bytes.add(raceXML.createMessage());

        BinaryMessage boatsXML = new XMLMessage(dataGenerator.loadFile("Boats.xml"), (short)0, XMLSubTypes.BOAT.getSubType(), (short) 0);
        bytes.add(boatsXML.createMessage());

        BinaryMessage regattaXML = new XMLMessage(dataGenerator.loadFile("Regatta.xml"), (short)0, XMLSubTypes.REGATTA.getSubType(), (short) 0);
        bytes.add(regattaXML.createMessage());
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (Boat boat : race.getBoats()) {
                BinaryMessage boatLocationMessage = new BoatLocationMessage(
                        1, System.currentTimeMillis(), boat.getSourceId(),
                        1, 1,
                        boat.getLatitude(), boat.getLongitude(), 0,
                        (short) boat.getHeading(), 0, 0,0,
                        (short) 100, boat.getSpeed(),
                        (short) 200, (short) 200,
                        (short) 100, (short) 100, (short) 100,
                        (short) 100, (short) 100, (short) 100
                );
                bytes.add(boatLocationMessage.createMessage());
            }
        }
    }

    class RaceRunner extends TimerTask {
        @Override
        public void run() {
            race.updateBoats();
            race.updateRaceInfo();
        }
    }

    class RSMSender extends TimerTask {
        @Override
        public void run() {
            rsm = new RaceStatusMessage(currentTimeMillis(),
                    race.getRaceID(),
                    race.getRaceStatus(),
                    race.getStartingTime().getTime(),
                    race.updateWindDirection(),
                    race.retrieveWindSpeed(),   // retrieve a new randomly generated wind speed
                    (char)(race.getBoats().size() + 48),
                    race.getRaceType(),
                    race.getBoats());
            bytes.add(rsm.createMessage());
        }
    }

}
