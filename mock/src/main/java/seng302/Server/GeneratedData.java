package seng302.Server;

import seng302.Boat;
import seng302.DataGenerator;
import seng302.Message;
import seng302.Race;
import seng302.packetGeneration.RaceStatusMessage;

import java.util.*;

/**
 * Created by sba136 on 3/05/17.
 */
public class GeneratedData implements IServerData {
    private Queue<byte[]> bytes = new LinkedList<>();
    private Message message = new Message();
    private Race race = new Race();

    // Hardcoded race
    private RaceStatusMessage rsm = new RaceStatusMessage(1,
                                                            2l,
                                                            3,
                                                            4,
                                                            5l,
                                                            6,
                                                            7,
                                                            8,
                                                            9,
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
            System.out.println("Race Status Message created");
        }
    }

    public void runServerTimers() {
        Timer timer = new Timer();
        timer.schedule(new RSMSender(), 0, Long.MAX_VALUE);
        timer.schedule(new XMLSender(), 0, 2000);
        timer.schedule(new BoatPosSender(), 0, 500);
        timer.schedule(new RaceRunner(), 0, 17);
    }
}
