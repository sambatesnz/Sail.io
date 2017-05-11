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
        this.currentTime = getCurrentTime(currentTime);
    }

    public RaceStatusMessage(int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int raceType, List<Boat> boats) {

    }



    private byte[] convertTobytes(int number){
        return null;
    }

    public byte[] getRaceStatusMessage(){
        byte[] output = new byte[24 + boatNum * 20];
        //Copy specific byes into here

        for (int i = RaceStatusUtility.CURRENT_TIME; i <RaceStatusUtility.CURRENT_TIME + RaceStatusUtility.CURRENT_TIME_SIZE ; i++) {
            output[i] = currentTime[i-1];
        }
        return output;
    }

    private byte[] getCurrentTime(){
        byte[] time = RaceStatusUtility.LEBuffer(8).putLong(System.currentTimeMillis()).array();
        return Arrays.copyOfRange(time, 2, 8);
    }

    private byte[] getCurrentTime(long currentTime){
        byte[] time = RaceStatusUtility.LEBuffer(8).putLong(currentTime).array();
        byte[] smallerTime = Arrays.copyOfRange(time, 0, 6);
        return smallerTime;
    }
}
