package seng302.ManualTesting.MessageTypeGeneration.DataGenerators;

import seng302.DataGeneration.IServerData;
import seng302.DataGenerator;
import seng302.Modes.Race;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.BoatLocationGeneration.BoatLocationMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLSubTypes;
import seng302.Position;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class MarkLocationDataGenerator implements IServerData {
    private Queue<byte[]> bytes = new LinkedBlockingQueue<>();

    private Timer timer = new Timer();

    @Override
    public Race getRace() {
        return null;
    }

    @Override
    public byte[] getDataForAll() {
        try {
            return bytes.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    @Override
    public byte[] getDataForOne() {
        return new byte[0];
    }

    @Override
    public void addSingleMessage(byte[] message) {

    }

    @Override
    public void addMessage(byte[] message) {

    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public boolean broadcastReady() {
        return !bytes.isEmpty();
    }

    @Override
    public boolean singleMessageReady() {
        return false;
    }

    @Override
    public void beginGeneratingData() {
        timer.schedule(new RaceXMLSender(), 0, 2000);
        timer.schedule(new BoatLocationSender(), 0, 100);
    }

    @Override
    public void finishGeneratingData() {
        System.out.println("Threads cancelled");
        timer.cancel();
    }

    @Override
    public void addXMLPackets() {

    }

    private class RaceXMLSender extends TimerTask {
        @Override
        public void run() {
            DataGenerator dataGenerator = new DataGenerator();
            BinaryMessage raceXml = new XMLMessage(dataGenerator.loadFile("Race.xml"), (short)0, XMLSubTypes.RACE.getSubType(), (short) 0);
            bytes.add(raceXml.createMessage());
        }
    }

    private class BoatLocationSender extends TimerTask {
        @Override
        public void run() {
            int sourceId = 130;
            double lat = 7193973.58254064;
            double lon = -7217662.062757881;
            double range = 100;

            double latitude = Position.convertY(ThreadLocalRandom.current().nextDouble(lat-range/2, lat+range/2));
            double longitude = Position.convertX(ThreadLocalRandom.current().nextDouble(lon-range/2, lon+range/2));
            BinaryMessage locationMessage = new BoatLocationMessage(
                    1, System.currentTimeMillis(), sourceId,
                    1, 1,
                    latitude, longitude, 0,
                    (short) 0, 0, 0, (short) 0,
                    (short) 100, (short) 100,
                    (short) 200, (short) 200,
                    (short) 100, (short) 100, (short) 100,
                    (short) 100, (short) 100, (short) 100
            );

            bytes.add(locationMessage.createMessage());
        }
    }
}
