package seng302.Client.Messages;


import seng302.Race.Boat;
import seng302.Race.Mark;
import seng302.Race.Race;

import java.util.Map;

/**
 * Class that holds and can update the location of a boat in a yacht race,
 * contained in the boat location packets
 */

public class LocationMessage{
    private long time;
    private int sourceID;
    private double latitude;
    private double longitude;
    private double heading;
    private int speedOverGround;
    private Race race;

    private Map<Integer, Boat> boatDict;


    /**
     * Constructor for the class. Takes in an array of bytes from a boat location
     * packet as a parameter, and extracts the relevant information from it so it can
     * be passed on to the relevant boat.
     * @param bytes The array of bytes from the body of a boat location packet
     */
    public LocationMessage(byte[] bytes, Race race) {
        time = Message.byteArrayToLong(bytes, 1, 6);
        sourceID = Message.byteArrayToInt(bytes, 7, 4);
//        seqNumber = Message.byteArrayToInt(bytes, 11, 6);
        latitude = Message.byteArrayToLong(bytes, 16, 4) * 180 / 2147483648.0;
        longitude = Message.byteArrayToLong(bytes, 20, 4) * 180 / 2147483648.0;
        heading = Message.byteArrayToInt(bytes, 28, 2) * 360 / 65536.0;
        speedOverGround = Math.toIntExact((long) (Message.byteArrayToInt(bytes, 38, 2) * 1.9438444924574 / 1000));

        this.race = race;
        boatDict = race.boats;

        setBoatLocation();
    }

    /**
     * Updates the following location values of the boat given by the source ID:
     * - Latitude
     * - Longitude
     * - Speed (over ground)
     * - Heading
     */
    private void setBoatLocation() {
        if (boatDict.containsKey(sourceID)) {
            boatDict.get(sourceID).setMark(new Mark(latitude, longitude));
            boatDict.get(sourceID).setSpeed(speedOverGround);
            boatDict.get(sourceID).setHeading(heading);
        }
    }
}

