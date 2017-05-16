package seng302.packetGeneration.RaceStatusGeneration;

import seng302.Boat;

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
    private byte[] numberOfBoatsByte;
    private byte[] raceType;
    private List<byte[]> boats;
    private int numberOfBoatsInt;

    public static int CURRENT_VERSION_NUMBER= 2;

    /**
     * Constructor for the Race Status BoatLocationMessage, Used when you don't know what version number to give
     * @param currentTime current local time milliseconds
     * @param raceID Id of the race
     * @param raceStatus Status of the race eg Active
     * @param startTime Time the race will start in milliseconds from Jan 1, 1970
     * @param windDirection Direction of wind as an unsigned short
     * @param windSpeed Speed of win in mm/sec
     * @param numberOfBoats Number of boats in the race
     * @param raceType Type of race e.g. Match or Fleet Race
     * @param boats List of boats in the race
     */
    public RaceStatusMessage(long currentTime, int raceID, int raceStatus, long startTime, short windDirection, short windSpeed, char numberOfBoats, char raceType, List<Boat> boats) {
        this.versionNumber = RaceStatusUtility.intToOneByte(CURRENT_VERSION_NUMBER);
        this.currentTime = RaceStatusUtility.longToSixBytes(currentTime);
        this.raceID = RaceStatusUtility.intToFourBytes(raceID);
        this.raceStatus = RaceStatusUtility.intToFourBytes(raceStatus);
        this.startTime = RaceStatusUtility.longToSixBytes(startTime);
        this.windDirection = RaceStatusUtility.shortToTwoBytes(windDirection);
        this.windSpeed = RaceStatusUtility.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = RaceStatusUtility.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = RaceStatusUtility.charToOneByte(raceType);
    }

    /**
     * Constructor for the Race Status message.
     * @param versionNumber Version number of the Race Status BoatLocationMessage Packet
     * @param currentTime current local time milliseconds
     * @param raceID Id of the race
     * @param raceStatus Status of the race eg Active
     * @param startTime Time the race will start in milliseconds from Jan 1, 1970
     * @param windDirection Direction of wind as an unsigned short
     * @param windSpeed Speed of win in mm/sec
     * @param numberOfBoats Number of boats in the race
     * @param raceType Type of race e.g. Match or Fleet Race
     * @param boats List of boats in the race
     */
    public RaceStatusMessage(int versionNumber, long currentTime, int raceID, int raceStatus, long startTime, short windDirection, short windSpeed, char numberOfBoats, char raceType, List<Boat> boats) {
        this.versionNumber = RaceStatusUtility.intToOneByte(versionNumber);
        this.currentTime = RaceStatusUtility.longToSixBytes(currentTime);
        this.raceID = RaceStatusUtility.intToFourBytes(raceID);
        this.raceStatus = RaceStatusUtility.intToFourBytes(raceStatus);
        this.startTime = RaceStatusUtility.longToSixBytes(startTime);
        this.windDirection = RaceStatusUtility.shortToTwoBytes(windDirection);
        this.windSpeed = RaceStatusUtility.shortToTwoBytes(windSpeed);
        this.numberOfBoatsByte = RaceStatusUtility.charToOneByte(numberOfBoats);
        this.numberOfBoatsInt = Character.getNumericValue(numberOfBoats);
        this.raceType = RaceStatusUtility.charToOneByte(raceType);
    }

    /**
     * Gets the Race Status message as a byte Array
     * @return byte array containing the whole Race Status BoatLocationMessage
     */
    public byte[] getRaceStatusMessage(){
        byte[] output = new byte[24 + numberOfBoatsInt * 20];
        System.out.println(numberOfBoatsInt);
        //Copy specific bytes into here
        System.arraycopy(versionNumber, 0, output, RaceStatusUtility.MESSAGE_VERSION_POS, RaceStatusUtility.MESSAGE_VERSION_SIZE);
        System.arraycopy(currentTime, 0, output, RaceStatusUtility.CURRENT_TIME_POS, RaceStatusUtility.CURRENT_TIME_SIZE);
        System.arraycopy(raceID, 0, output, RaceStatusUtility.RACE_ID_POS, RaceStatusUtility.RACE_ID_SIZE);
        System.arraycopy(raceStatus, 0, output, RaceStatusUtility.RACE_STATUS_POS, RaceStatusUtility.RACE_STATUS_SIZE);
        System.arraycopy(startTime, 0, output, RaceStatusUtility.EXPECTED_START_TIME_POS, RaceStatusUtility.EXPECTED_START_TIME_SIZE);
        System.arraycopy(windDirection, 0, output, RaceStatusUtility.WIND_DIRECTION_POS, RaceStatusUtility.WIND_DIRECTION_SIZE);
        System.arraycopy(windSpeed, 0, output, RaceStatusUtility.WIND_SPEED_POS, RaceStatusUtility.WIND_SPEED_SIZE);
        System.arraycopy(numberOfBoatsByte, 0, output, RaceStatusUtility.NUM_BOATS_POS, RaceStatusUtility.NUM_BOATS_SIZE);
        System.arraycopy(raceType, 0, output, RaceStatusUtility.RACE_TYPE_POS, RaceStatusUtility.RACE_TYPE_SIZE );
        return output;
    }
}