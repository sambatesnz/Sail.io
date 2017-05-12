package seng302.packetGeneration;

import seng302.Boat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

/**
 * For creating race status packets to be sent by the server
 *
 * The primary concern of this class is to copy byte arrays of info (eg currentTime
 * Into the correct location for the race status message packet
 */
public class RaceStatusMessage {

    private byte[] versionNumber;
    private byte[] currentTime;
    private byte[] raceID;
    private byte[] raceStatus;
    private byte[] startTime;
    private byte[] windDirection;
    private byte[] windSpeed;
    private byte[] numberOfBoats;
    private byte[] raceType;
    private List<byte[]> boats;
    private int boatNum;

    public RaceStatusMessage(int versionNumber, long currentTime, int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int numberOfBoats, int raceType, List<Boat> boats) {
        this.boatNum = numberOfBoats;
        this.currentTime = RaceStatusUtility.longToSixBytes(currentTime);
        this.startTime = RaceStatusUtility.longToSixBytes(startTime);
    }

    public RaceStatusMessage(int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int raceType, List<Boat> boats) {

    }

    public byte[] getRaceStatusMessage(){
        byte[] output = new byte[24 + boatNum * 20];
        //Copy specific bytes into here

        System.arraycopy(currentTime, 0, output, RaceStatusUtility.CURRENT_TIME, RaceStatusUtility.CURRENT_TIME_SIZE);
        System.arraycopy(startTime, 0, output, RaceStatusUtility.EXPECTED_START_TIME, RaceStatusUtility.EXPECTED_START_TIME_SIZE);

        return output;
    }

    private byte[] convertTobytes(int number){
        BoatStatusMessage b = new BoatStatusMessage(3, RaceStatusUtility.BoatStatus.FINISHED.value(), (char)1, (long) 1, (long) 1);
        return null;
    }
}
