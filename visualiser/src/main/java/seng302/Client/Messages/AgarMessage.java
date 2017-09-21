package seng302.Client.Messages;

import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Race;

import java.util.Map;


/**
 * Class that holds and can update the agar of a boat in a agar race,
 * contained in the boat agar packets
 */

public class AgarMessage {
    private int boatLives;
    private int boatSize;
    private int boatSourceId;
    private Race race;
    private Map<Integer, GenericBoat> boatDict;

    /**
     * Constructor
     * @param bytes the message
     * @param race the race
     */
    public AgarMessage(byte[] bytes, Race race) {
        boatSourceId = PacketParserUtils.byteArrayToInt(bytes, 0, 4);
        boatLives = PacketParserUtils.byteArrayToInt(bytes, 4, 4);
        boatSize = PacketParserUtils.byteArrayToInt(bytes, 8, 4);
        boatDict = race.getBoatsMap();
        this.race = race;

        setAgarBoatStats();
    }

    /**
     * Updates the size and lives of an agar boat.
     *
     */
    private void setAgarBoatStats() {
        if (boatDict != null && boatDict.containsKey(boatSourceId)) {
            GenericBoat boat = boatDict.get(boatSourceId);
            boat.setLives(boatLives);
            boat.setAgarSize(boatSize);
        }
    }
}

