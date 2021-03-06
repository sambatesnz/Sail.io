package seng302.ManualTesting.MessageTypeGeneration.DataGenerators;

import seng302.DataGeneration.IServerData;
import seng302.DataGenerator;
import seng302.Modes.Race;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLMessage;
import seng302.PacketGeneration.XMLMessageGeneration.XMLSubTypes;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sha162 on 24/07/17.
 */
public class RaceXmlDataGenerator implements IServerData{
    private Queue<byte[]> bytes = new LinkedBlockingQueue<>();

    Timer timer = new Timer();


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
}
