package seng302.Client.Messages;

import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Race;

import java.util.Map;


/**
 * Class that holds and can update the agar of a boat in a agar race,
 * contained in the boat agar packets
 */

public class AgarMessage extends ClientSideMessageParser {
    private int boatLives;
    private int boatSize;
    private int boatSourceId;
    private Race race;
    private Map<Integer, GenericBoat> boatDict;

    /**
     * Constructor
     * @param bytes the message
     */
    public AgarMessage(byte[] bytes) {
        super(bytes);
        boatSourceId = PacketParserUtils.byteArrayToInt(bytes, 0, 4);
        boatLives = PacketParserUtils.byteArrayToInt(bytes, 4, 4);
        boatSize = PacketParserUtils.byteArrayToInt(bytes, 8, 4);
    }

    @Override
    public void updateRace(Race race) {
        boatDict = race.getBoatsMap();
        if (boatDict != null && boatDict.containsKey(boatSourceId)) {
            GenericBoat boat = boatDict.get(boatSourceId);
            while (boat.getLives() > boatLives) {
                boat.loseLife();
            }
            System.out.println("boat with source id" + boat.getSourceId() + " has lives: " + boat.getLives());
            boat.setAgarSize(boatSize);
            if (boat.isEliminated()) {
                boat.setSpeed(0); //Cant move because its eliminated
            }
        }
    }

    public int getBoatSourceId() {
        return boatSourceId;
    }

    public int getLives() {
        return boatLives;
    }

    public int getBoatSize() {
        return boatSize;
    }
}

