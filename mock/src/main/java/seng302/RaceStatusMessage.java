package seng302;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * For creating race status packets to be sent by the server
 */
public class RaceStatusMessage {

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
        return new byte[24 + boatNum * 20];
    }
}
