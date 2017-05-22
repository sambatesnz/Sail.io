package seng302.Messages;


import seng302.*;
import java.util.HashMap;

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


    /**
     * Constructor for the class. Takes in an array of bytes from a boat location
     * packet as a parameter, and extracts the relevant information from it so it can
     * be passed on to the relevant boat.
     * @param bytes The array of bytes from the body of a boat location packet
     */
    public LocationMessage(byte[] bytes) {
//        messageVersionNumber = data[0];
        time = Message.byteArrayToLong(bytes, 1, 6);
        sourceID = Message.byteArrayToInt(bytes, 7, 4);
//        seqNumber = Message.byteArrayToInt(bytes, 11, 6);
        latitude = Message.byteArrayToLong(bytes, 16, 4) * 180 / 2147483648.0;
        longitude = Message.byteArrayToLong(bytes, 20, 4) * 180 / 2147483648.0;
        heading = Message.byteArrayToInt(bytes, 28, 2) * 360 / 65536.0;
        speedOverGround = Math.toIntExact((long) (Message.byteArrayToInt(bytes, 38, 2) * 1.9438444924574 / 1000));
    }

    public int getSourceID() {
        return sourceID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getHeading() {
        return heading;
    }

    public int getSpeedOverGround() {
        return speedOverGround;
    }
}

