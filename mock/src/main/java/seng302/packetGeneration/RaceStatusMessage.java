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

    public RaceStatusMessage(int versionNumber, long currentTime, int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, char numberOfBoats, char raceType, List<Boat> boats) {
        this.currentTime = RaceStatusUtility.longToSixBytes(currentTime);
        this.raceID = RaceStatusUtility.intToFourBytes(raceID);
        this.raceStatus = RaceStatusUtility.intToFourBytes(raceStatus);
        this.startTime = RaceStatusUtility.longToSixBytes(startTime);
        this.numberOfBoats = RaceStatusUtility.charToOneByte(numberOfBoats);
        this.boatNum = Character.getNumericValue(numberOfBoats);
        this.raceType = RaceStatusUtility.charToOneByte(raceType);
    }

    public RaceStatusMessage(int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int raceType, List<Boat> boats) {

    }

    public byte[] getRaceStatusMessage(){
        byte[] output = new byte[24 + boatNum * 20];
        //Copy specific bytes into here

        System.arraycopy(currentTime, 0, output, RaceStatusUtility.CURRENT_TIME, RaceStatusUtility.CURRENT_TIME_SIZE);
        System.arraycopy(raceID, 0, output, RaceStatusUtility.RACE_ID, RaceStatusUtility.RACE_ID_SIZE);
        System.arraycopy(raceStatus, 0, output, RaceStatusUtility.RACE_STATUS, RaceStatusUtility.RACE_STATUS_SIZE);
        System.arraycopy(startTime, 0, output, RaceStatusUtility.EXPECTED_START_TIME, RaceStatusUtility.EXPECTED_START_TIME_SIZE);
        System.arraycopy(numberOfBoats, 0, output, RaceStatusUtility.NUM_BOATS, RaceStatusUtility.NUM_BOATS_SIZE);
        System.arraycopy(raceType, 0, output, RaceStatusUtility.RACE_TYPE, RaceStatusUtility.RACE_TYPE_SIZE );


        return output;
    }

    private byte[] convertTobytes(int number){
        return null;
    }
}
