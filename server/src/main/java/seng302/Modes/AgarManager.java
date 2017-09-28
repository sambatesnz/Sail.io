package seng302.Modes;

import seng302.BoatManager;
import seng302.DataGeneration.BoatXMLCreator;
import seng302.DataGeneration.IServerData;
import seng302.DataGenerator;
import seng302.PacketGeneration.AgarPackets.ServerAgarMessage;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketGeneration.RaceStatusGeneration.RaceStatusMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLSubTypes;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.RaceObjects.GenericBoat;
import seng302.XMLCreation.RaceXMLCreator;
import seng302.XMLCreation.XMLCreator;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.currentTimeMillis;

public class AgarManager implements IServerData{

    private Race race;
    private BinaryMessage rsm;
    private Queue<byte[]> broadcastMessageQueue;
    private Queue<byte[]> singularMessageQueue;
    private Timer timer = new Timer();
    private BoatManager boatManager;
    private AgarManager parent = this;

    public AgarManager(){
        this.race = new AgarRace();
        race.parseCourseXML("Race.xml");
        race.parseRaceXML("Race.xml");
        race.setUp();
        boatManager = race.getBoatManager();
        broadcastMessageQueue = new LinkedBlockingQueue<>();
        singularMessageQueue = new LinkedBlockingQueue<>();
    }


    public Race getRace() {
        return race;
    }

    /**
     * adds a message to the broadcast queue
     * @param message the message you want to add
     */
    @Override
    public void addMessage(byte[] message) {
        broadcastMessageQueue.add(message);
    }

    @Override
    public byte[] getDataForAll() {
        try {
            return broadcastMessageQueue.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    @Override
    public byte[] getDataForOne() {
        try {
            return singularMessageQueue.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    @Override
    public void addSingleMessage(byte[] message) {
        singularMessageQueue.add(message);
    }

    @Override
    public boolean finished() {
        return  race.getRaceStatus() == RaceStatus.FINISHED;
    }

    @Override
    public boolean broadcastReady() {
        return !broadcastMessageQueue.isEmpty();
    }

    @Override
    public boolean singleMessageReady(){
        return !singularMessageQueue.isEmpty();
    }

    @Override
    public void beginGeneratingData() {
        timer.schedule(new AgarManager.XMLSender(), 0, 2000);
        timer.schedule(new AgarManager.RSMSender(), 100, 500);
        timer.schedule(new AgarManager.BoatPosSender(), 100, 17);
        timer.schedule(new AgarManager.RaceRunner(), 200, 17);
        timer.schedule(new AgarManager.raceEventHandler(), 200, 17);
    }

    @Override
    public void finishGeneratingData() {
        System.out.println("Threads cancelled");
        timer.cancel();
    }

    @Override
    public void addXMLPackets() throws IOException {
        generateXML();
    }

    class XMLSender extends TimerTask {
        @Override
        public void run() {
            try {
                generateXML();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateXML() throws IOException {
        DataGenerator dataGenerator = new DataGenerator();

        XMLCreator raceXMLCreator = new RaceXMLCreator(race);
        XMLCreator boatXMLCreator = new BoatXMLCreator(race.getBoats());


        BinaryMessage raceXML =  new XMLMessage(raceXMLCreator.getXML(), (short)0, XMLSubTypes.RACE.getSubType(),  (short) 0);
        broadcastMessageQueue.add(raceXML.createMessage());

        BinaryMessage boatsXML = new XMLMessage(boatXMLCreator.getXML(), (short)0, XMLSubTypes.BOAT.getSubType(), (short) 0);
        broadcastMessageQueue.add(boatsXML.createMessage());

        BinaryMessage regattaXML = new XMLMessage(dataGenerator.loadFile("Regatta.xml"), (short)0, XMLSubTypes.REGATTA.getSubType(), (short) 0);
        broadcastMessageQueue.add(regattaXML.createMessage());
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (GenericBoat boat : race.getBoats()) {
                BinaryMessage boatLocationMessage = new BoatLocationMessage(
                        1, System.currentTimeMillis(), boat.getSourceId(),
                        1, 1,
                        boat.getLatitude(), boat.getLongitude(), 0,
                        (short) boat.getHeading(), 0, 0,0,
                        (short) 100, boat.getSpeed()/150,
                        (short) 200, (short) 200,
                        (short) 100, (short) 100, (short) 100,
                        (short) (boat.isSailsOut() ? 1 : 0), (short) 100, (short) 100
                );
                BinaryMessage agarMessage = new ServerAgarMessage(boat.getSourceId(), boat.getLives(), boat.getAgarSize());
                broadcastMessageQueue.add(boatLocationMessage.createMessage());
                broadcastMessageQueue.add(agarMessage.createMessage());
            }
        }
    }


    class raceEventHandler extends TimerTask {
        @Override
        public void run() {
            if (boatManager.hasABoatFinished()) {
                GenericBoat boat = boatManager.getFinishedBoat();
                BinaryMessage yachtEventMessage = new YachtEventMessage(
                        boat.getSourceId(),
                        YachtIncidentEvent.FINISHED
                );
                broadcastMessageQueue.add(yachtEventMessage.createMessage());
            }
        }
    }


    class RaceRunner extends TimerTask {
        @Override
        public void run() {
            if(race.getRaceStatus() != RaceStatus.WARNING && race.getRaceStatus() != RaceStatus.START_TIME_NOT_SET && race.getRaceStatus() != RaceStatus.PREP) {
                race.updateBoats();
                race.checkCollisions(parent);
            }
            race.updateRaceInfo();
        }
    }

    class RSMSender extends TimerTask {
        @Override
        public void run() {
            rsm = new RaceStatusMessage(currentTimeMillis(),
                    race.getRaceID(),
                    race.getRaceStatus().value(),
                    race.getStartingTime().getTime(),
                    race.updateWindDirection(),
                    race.retrieveWindSpeed(),   // retrieve a new randomly generated wind speed
                    (char)(race.getBoats().size() + 48),
                    race.getRaceType(),
                    race.getBoats());
            broadcastMessageQueue.add(rsm.createMessage());
        }
    }
}
