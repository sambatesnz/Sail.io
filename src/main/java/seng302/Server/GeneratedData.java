package seng302.Server;

import javafx.animation.AnimationTimer;
import seng302.Boat;
import seng302.DataGenerator;
import seng302.Message;
import seng302.Race;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by sba136 on 3/05/17.
 */
public class GeneratedData implements IServerData {
    private Queue<byte[]> bytes = new LinkedList<>();
    private Message message = new Message();
    private Race race = new Race();
    private int i =0;

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
            byte[] xmlBytes = message.xmlMessage(dataGenerator.loadFile("Race.xml"), (short) 0, (short) 0);
//            System.out.println(Arrays.toString(xmlBytes) + "\nsize: " + (bytes.size()+1));
            bytes.add(xmlBytes);
        }
    }

    class BoatPosSender extends TimerTask {
        @Override
        public void run() {
            for (Boat boat : race.getBoats()) {
                bytes.add(message.boatPositionMessage(boat));
            }
        }
    }

    class RaceRunner extends TimerTask {
        @Override
        public void run() {
            race.updateBoats();
        }
    }

    public void runServerTimers() {
        Timer timer = new Timer();
        timer.schedule(new XMLSender(), 0, 2000);
        timer.schedule(new BoatPosSender(), 0, 500);
        timer.schedule(new RaceRunner(), 0, 17);
    }
}
