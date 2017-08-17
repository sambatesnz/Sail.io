package seng302.Client.Messages;

import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Mark;

import java.util.Map;

/**
 * Class that holds and can update the location of a boat in a yacht race,
 * contained in the boat location packets
 */

public class LocationMessage {
    private long time;
    private int sourceID;
    private double latitude;
    private double longitude;
    private double heading;
    private int speedOverGround;
    private boolean sailOut;
    private Race race;
    private Map<Integer, Boat> boatDict;


    /**
     * Constructor for the class. Takes in an array of bytes from a boat location
     * packet as a parameter, and extracts the relevant information from it so it can
     * be passed on to the relevant boat.
     * @param bytes The array of bytes from the body of a boat location packet
     * @param race The race being run
     */
    public LocationMessage(byte[] bytes, Race race) {
        time = PacketParserUtils.byteArrayToLong(bytes, 1, 6);
        sourceID = PacketParserUtils.byteArrayToInt(bytes, 7, 4);
//        seqNumber = PacketParserUtils.byteArrayToInt(bytes, 11, 6);
        latitude = PacketParserUtils.byteArrayToLong(bytes, 16, 4) * 180 / 2147483648.0;
        longitude = PacketParserUtils.byteArrayToLong(bytes, 20, 4) * 180 / 2147483648.0;
        heading = PacketParserUtils.byteArrayToInt(bytes, 28, 2) * 360 / 65536.0;
        speedOverGround = Math.toIntExact((long) (PacketParserUtils.byteArrayToInt(bytes, 38, 2)));
        boatDict = race.getBoatsMap();
        sailOut = bytes[50] == 1;
        this.race = race;

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
        if (boatDict != null && boatDict.containsKey(sourceID)) {
            Boat boat = boatDict.get(sourceID);
            boat.setMark(new Mark(latitude, longitude));
            boat.setSpeed(speedOverGround);
            boat.setHeading(heading);
            boat.setKnowsBoatLocation(true);
            boat.setSailsOut(sailOut);
        } else if (race.getMarks() != null && race.getMarks().containsKey(sourceID)) {
            Mark mark = race.getMarks().get(sourceID);
            mark.setLatitude(latitude);
            mark.setLongitude(longitude);
            mark.setY(mark.convertToY(latitude));
            mark.setX(mark.convertToX(longitude));
        }
    }
}

