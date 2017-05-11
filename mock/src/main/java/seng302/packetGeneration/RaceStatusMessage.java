package seng302.packetGeneration;

import seng302.Boat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

/**
 * For creating race status packets to be sent by the server
 */
public class RaceStatusMessage {

    public static int OFFSET = 1;
    public static int MESSAGE_VERSION = 0;
    public static int MESSAGE_VERSION_SIZE = 1;
    public static int CURRENT_TIME = MESSAGE_VERSION + OFFSET;
    public static int CURRENT_TIME_SIZE = 6;
    public static int RACE_ID = CURRENT_TIME + OFFSET;
    public static int RACE_ID_SIZE = 4;
    public static int RACE_STATUS = RACE_ID + OFFSET;
    public static int RACE_STATUS_SIZE = 1;
    public static int EXPECTED_START_TIME = RACE_STATUS + OFFSET;
    public static int EXPECTED_START_TIME_SIZE = 6;
    public static int WIND_DIRECTION = EXPECTED_START_TIME + OFFSET;
    public static int WIND_DIRECTION_SIZE = 2;
    public static int WIND_SPEED = WIND_DIRECTION + OFFSET;
    public static int WIND_SPEED_SIZE = 2;
    public static int NUM_BOATS = WIND_SPEED + OFFSET;
    public static int NUM_BOATS_SIZE = 1;
    public static int BOATS = NUM_BOATS + OFFSET;
    public static int BOATS_SIZE = 20;



    byte[] versionNumber;
    byte[] currentTime;
    byte[] raceID;
    byte[] raceStatus;
    byte[] startTime;
    byte[] windDirection;
    byte[] windSpeed;
    byte[] numberOfBoats;
    byte[] raceType;
    List<byte[]> boats;

    int boatNum;

    public RaceStatusMessage(int versionNumber, long currentTime, int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int numberOfBoats, int raceType, List<Boat> boats) {
        this.boatNum = numberOfBoats;
        this.currentTime = getCurrentTime(currentTime);
    }

    public RaceStatusMessage(int raceID, int raceStatus, long startTime, int windDirection, int windSpeed, int raceType, List<Boat> boats) {

    }

    private ByteBuffer LEBuffer(int capacity) {
        return ByteBuffer.allocate(capacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    private byte[] convertTobytes(int number){
        return null;
    }

    public byte[] getRaceStatusMessage(){
        byte[] output = new byte[24 + boatNum * 20];
        //Copy specific byes into here

        for (int i = CURRENT_TIME; i <CURRENT_TIME + CURRENT_TIME_SIZE ; i++) {
            output[i] = currentTime[i-1];
        }
        return output;
    }

    private byte[] getCurrentTime(){
        byte[] time = LEBuffer(8).putLong(System.currentTimeMillis()).array();
        return Arrays.copyOfRange(time, 2, 8);
    }

    private byte[] getCurrentTime(long currentTime){
        byte[] time = LEBuffer(8).putLong(currentTime).array();
        byte[] smallerTime = Arrays.copyOfRange(time, 0, 6);
        return smallerTime;
    }
}
